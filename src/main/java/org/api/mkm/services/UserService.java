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
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class UserService {

	private Logger logger = LogManager.getLogger(this.getClass());
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
	
	public List<User> findUsers(String name) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/users/find?search="+name.toLowerCase();
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		if(res.getErrors()!=null)
			throw new MkmException(res.getErrors());
	 	
		return res.getUsers();
		
	 	
	}
	
	
	public boolean setVacation(boolean vacation) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/account?onVacation="+vacation;
		Tools.getXMLResponse(link, "PUT", this.getClass());
	 	return true;
	}
	
	public boolean sendMessage(User u, String message)throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/account/messages/"+u.getIdUser();
		StringBuilder temp = new StringBuilder();
		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request><message>"+message+"</message>");
		temp.append("</request>");
	
		Tools.postXMLResponse(link, "POST", this.getClass(),temp.toString());
		
		return true;
	}
	
	public List<Thread> getMessages(User other) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/account/messages";
		
		if(other!=null)
			link+="/"+other.getIdUser();
		
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
        Response res = (Response)xstream.fromXML(xml);
        
		return res.getThread();
	 	
	}
	
	
	
}
