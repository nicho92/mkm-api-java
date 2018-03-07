package org.api.mkm.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
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
import org.api.mkm.modele.Response;
import org.api.mkm.modele.Thread;
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
 		xstream.addImplicitCollection(Response.class,"users",User.class);
 		xstream.addImplicitCollection(Thread.class,"links",Link.class);
 		
	}
	
	public List<User> findUsers(String name) throws IOException, MkmNetworkException, MkmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/users/find?search="+name.toLowerCase();
		logger.debug("LINK="+link);
		
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
		if(res.getErrors()!=null)
			throw new MkmException(res.getErrors());
	 	
		return res.getUsers();
		
	 	
	}
	
	
	public boolean setVacation(boolean vacation) throws IOException, MkmNetworkException, MkmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/account?onVacation="+vacation;
		logger.debug("LINK="+link);
		
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
	
	public boolean sendMessage(User u, String message)throws IOException, MkmNetworkException, MkmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/account/messages/"+u.getIdUser();
		
		logger.debug("LINK="+link);
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
				            connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"POST")) ;
				       		connection.setDoOutput(true);
				    		connection.setRequestMethod("POST");
				    		connection.connect();
				    		
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		StringBuilder temp = new StringBuilder();
		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request><message>"+message+"</message>");
		temp.append("</request>");
		logger.debug("REQU="+temp);
		
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean code= connection.getResponseCode()>=200 && connection.getResponseCode()<300;
		
		if(code)
		{
			String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
			logger.debug("RESP="+xml);
			Response res = (Response)xstream.fromXML(xml);
			if(res.getErrors()!=null)
				throw new MkmException(res.getErrors());
			
			return code;
		}
		else
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		
		
	}
	
	public List<Thread> getMessages(User other) throws IOException, MkmNetworkException, MkmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/account/messages";
		
		if(other!=null)
			link+="/"+other.getIdUser();
		
		logger.debug("LINK="+link);
		
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
