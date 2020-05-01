package org.api.mkm.services;

import java.io.IOException;
import java.util.List;

import org.api.mkm.exceptions.MkmException;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.Thread;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class UserService {

	private XStream xstream;
	
	public UserService() {
		
		xstream = Tools.instNewXstream();
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
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><message>"+message+"</message>");
		temp.append("</request>");
	
		Tools.getXMLResponse(link, "POST", this.getClass(),temp.toString());
		
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
