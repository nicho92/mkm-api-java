package org.api.mkm.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Thread;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmAPIConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class UserService {

	static final Logger logger = LogManager.getLogger(UserService.class.getName());
	private XStream xstream;
	private AuthenticationServices auth;
	
	public UserService() {
		
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
		XStream.setupDefaultSecurity(xstream);
 		xstream.addPermission(AnyTypePermission.ANY);
 		xstream.alias("response", Response.class);
 		xstream.ignoreUnknownElements();
 		xstream.addImplicitCollection(Response.class,"links",Link.class);
 		xstream.addImplicitCollection(Response.class,"thread",Thread.class);
 		xstream.addImplicitCollection(Thread.class,"links",Link.class);
 		
	}
	
	public User getUser() throws MkmException, IOException, MkmNetworkException
	{
		return MkmAPIConfig.getInstance().getAuthenticator().getAuthenticatedUser();
	}
	
	public boolean setVacation(boolean vacation) throws IOException, MkmNetworkException, MkmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/account?onVacation="+vacation;
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
			               
		//String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
	 	return true;
	}
	
	
	public List<Thread> getMessages(User other) throws IOException, MkmNetworkException, MkmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/account/messages";
		
		if(other!=null)
			link+="/"+other.getIdUser();
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
	 	
	    String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		 
        logger.debug("RESP="+xml);
        Response res = (Response)xstream.fromXML(xml);
        
		return res.getThread();
	 	
	}
	
	
	
}
