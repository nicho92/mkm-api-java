package org.api.mkm.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Game;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class GameService {

	private AuthenticationServices auth;
	private XStream xstream;
	private Logger logger = LogManager.getLogger(this.getClass());
	private List<Game> games;
	
	public GameService() {
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
	
	
	public Game getGame(int id) throws IOException
	{
		if(games==null)
			games=listGames();
		
		for(Game g : games)
			if(g.getIdGame()==id)
				return g;
		
		return null;
		
	}
	
	
	public List<Game> listGames() throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/games";
		logger.debug(MkmConstants.MKM_LINK_PREFIX+link);
			
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());

			               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		

		Response res = (Response)xstream.fromXML(xml);
		games=res.getGame();
		
		return games;
		
	}
	
	
	public List<Expansion> listExpansion(Integer id) throws IOException
	{
		Game g = new Game();
		g.setIdGame(id);
		return listExpansion(g);
	}
	
	public List<Expansion> listExpansion(Game g) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/games/"+g.getIdGame()+"/expansions";
		logger.debug(MkmConstants.MKM_LINK_PREFIX+link);
			
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());

		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.trace(xml);
		Response res = (Response)xstream.fromXML(xml);
		
		return res.getExpansion();
	}
	
	
	
	

}
