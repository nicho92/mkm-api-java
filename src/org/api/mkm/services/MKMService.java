package org.api.mkm.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.AbstractMKMException;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Game;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmAPIConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class MKMService {

	private AuthenticationServices auth;
	private XStream xstream;
	static final Logger logger = LogManager.getLogger(MKMService.class.getName());
	
	
	public MKMService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"game",Game.class);
	 		xstream.addImplicitCollection(Response.class, "expansion", Expansion.class);
	 		xstream.addImplicitCollection(Response.class, "links", Link.class);
	 		xstream.ignoreUnknownElements();
	}
	
	
	public List<Game> listGames() throws MalformedURLException, IOException, InvalidKeyException, AbstractMKMException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/games";
		logger.debug("LINK="+link);
			
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		

		Response res = (Response)xstream.fromXML(xml);
		return res.getGame();
		
	}
	public List<Expansion> listExpansion(Integer id) throws MalformedURLException, IOException, InvalidKeyException, AbstractMKMException
	{
		Game g = new Game();
		g.setIdGame(id);
		return listExpansion(g);
	}
	public List<Expansion> listExpansion(Game g) throws MalformedURLException, IOException, InvalidKeyException, AbstractMKMException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/games/"+g.getIdGame()+"/expansions";
		logger.debug("LINK="+link);
			
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		Response res = (Response)xstream.fromXML(xml);
		
		return res.getExpansion();
	}
	
}
