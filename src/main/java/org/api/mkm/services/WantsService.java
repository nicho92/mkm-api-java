package org.api.mkm.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Localization;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.tools.IntConverter;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmBooleanConverter;
import org.api.mkm.tools.MkmConstants;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class WantsService {

	private AuthenticationServices auth;
	private XStream xstream;
	private Logger logger = LogManager.getLogger(this.getClass());
	
	
	public WantsService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"wantslist", Wantslist.class);
	 		xstream.addImplicitCollection(Wantslist.class,"item", WantItem.class);
	 		xstream.addImplicitCollection(Wantslist.class,"links", Link.class);
	 		xstream.addImplicitCollection(Product.class,"localization",Localization.class);
	 		xstream.addImplicitCollection(WantItem.class,"idLanguage",Integer.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.ignoreUnknownElements();
	 		xstream.registerConverter(new IntConverter());
	 		xstream.registerConverter(new MkmBooleanConverter());
	}
	
	public Wantslist deleteItem(Wantslist li, WantItem it) throws IOException
	{
		List<WantItem> lst = new ArrayList<>();
		lst.add(it);
		
		return deleteItems(li, lst);
	}
	
	public Wantslist deleteItems(Wantslist li, List<WantItem> list) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+li.getIdWantslist();
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
				            connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"PUT")) ;
				       		connection.setDoOutput(true);
				    		connection.setRequestMethod("PUT");
				    		connection.connect();
				    		
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>deleteItem</action>");
		for(WantItem w : list)
		{
			temp.append("<want>");
			temp.append("<idWant>"+w.getIdWant()+"</idWant>");
		}		    
		temp.append("</want></request>");
		logger.debug(MkmConstants.MKM_LOG_REQUEST+temp);
		
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean code= connection.getResponseCode()>=200 && connection.getResponseCode()<300;
		
		if(code)
		{
			String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
			logger.debug(xml);
			Response res = (Response)xstream.fromXML(xml);
			if(res.getErrors()!=null)
				throw new MkmException(res.getErrors());
			
			Wantslist li2 = ((Response)xstream.fromXML(xml)).getWantslist().get(0);
			
			if(isEmpty(li2))
			{
				li2.setItem(new ArrayList<WantItem>());
				return li2;
			}
			
			return ((Response)xstream.fromXML(xml)).getWantslist().get(0);
		}
		else
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		
	}
	
	public boolean updateItem(Wantslist wl,WantItem it) throws IOException
	{
		
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
	   
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
				            connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"PUT")) ;
				       		connection.setDoOutput(true);
				    		connection.setRequestMethod("PUT");
				    		connection.connect();
				    		
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>editItem</action>");
			temp.append("<want>");
				temp.append("<idWant>").append(it.getIdWant()).append("</idWant>");
				temp.append("<idProduct>").append(it.getProduct().getIdProduct()).append("</idProduct>");
				temp.append("<count>").append(it.getCount()).append("</count>");
				temp.append("<wishPrice>").append(it.getWishPrice()).append("</wishPrice>");
				temp.append("<mailAlert>").append(it.isMailAlert()).append("</mailAlert>");
				temp.append("<isFoil>").append(it.isFoil()).append("</isFoil>");
				temp.append("<isAltered>").append(it.isAltered()).append("</isAltered>");
				temp.append("<isPlayset>").append(it.isPlayset()).append("</isPlayset>");
				temp.append("<isSigned>").append(it.isSigned()).append("</isSigned>");
				
				if(it.getMinCondition()!=null)
					temp.append("<minCondition>").append(it.getMinCondition()).append("</minCondition>");
				else
					temp.append("<minCondition/>");
			
				for(Integer i : it.getIdLanguage())
					temp.append("<idLanguage>").append(i).append("</idLanguage>");
				
			temp.append("</want>");
		temp.append("</request>");

		logger.debug(MkmConstants.MKM_LOG_REQUEST+temp);
		
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean code= connection.getResponseCode()>=200 && connection.getResponseCode()<300;
		if(code)
		{
			String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
			logger.debug(xml);
			Response res = (Response)xstream.fromXML(xml);
			if(res.getErrors()!=null)
				throw new MkmException(res.getErrors());
		
		}
		else
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		return code;
	}
	
	public List<Wantslist> getWantList() throws IOException
	{
    	String link = MkmConstants.MKM_API_URL+"/wantslist";
    	
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean code= connection.getResponseCode()>=200 && connection.getResponseCode()<300;
		
		if(!code)
			throw new MkmNetworkException(connection.getResponseCode());
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug(xml);
		Response res = (Response)xstream.fromXML(xml);
		return res.getWantslist();
	}
	
	public boolean addItem(Wantslist wl, WantItem item) throws IOException
	{
		ArrayList<WantItem> list = new ArrayList<>();
		list.add(item);
		return addItem(wl, list);
	}
	
	
	public boolean addItem(Wantslist wl, List<WantItem> items) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"PUT")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.connect();
			
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

		StringBuilder temp = new StringBuilder();

		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>addItem</action>");

		for(WantItem w : items)
		{
			temp.append("<product>");
			temp.append("<idProduct>"+w.getProduct().getIdProduct()+"</idProduct>");
			temp.append("<count>"+w.getCount()+"</count>");
			temp.append("<mailAlert>"+w.isMailAlert()+"</mailAlert>");
			
			if(!w.getIdLanguage().isEmpty())
				for(Integer i : w.getIdLanguage())
					temp.append("<idLanguage>"+i+"</idLanguage>");

			temp.append("<minCondition>"+w.getMinCondition()+"</minCondition>");

			if(w.getWishPrice()>0)
				temp.append("<wishPrice>"+w.getWishPrice()+"</wishPrice>");
			else
				temp.append("<wishPrice/>");
			
			if(w.isPlayset()!=null)
					temp.append("<isPlayset>"+w.isPlayset()+"</isPlayset>");
			
			temp.append("</product>");
		}		    
		temp.append("</request>");
		logger.debug(MkmConstants.MKM_LOG_REQUEST+temp);
		out.write(temp.toString());
		out.close();
		
		MkmAPIConfig.getInstance().updateCount(connection);
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
		
		if(ret)
    	{
    		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
    		logger.debug(xml);
    	}
		else
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		return ret;
	}
	
	public boolean renameWantList(Wantslist wl , String name) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
				            connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"PUT")) ;
				       		connection.setDoOutput(true);
				    		connection.setRequestMethod("PUT");
				    		connection.connect();
				    		
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>editWantslist</action>");
		temp.append("<name>").append(name).append("</name></request>");
		
		logger.debug(MkmConstants.MKM_LOG_REQUEST+temp);
		
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
		
		if(ret)
		{
			wl.setName(name);
			String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
			logger.debug(xml);
		}
		else
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		return ret;
		
	}
	
	public Wantslist createWantList(String name) throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/wantslist";
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		String temp = MkmConstants.XML_HEADER+"<request><wantslist><idGame>1</idGame><name>"+name+"</name></wantslist></request>";
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
        				  connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"POST")) ;
        				  connection.setDoOutput(true);
        				  connection.setRequestMethod("POST");
        				  connection.setRequestProperty( "charset", "utf-8");
        				  connection.connect() ;
        				  OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        				  out.write(temp);
        				  out.close();
         				  MkmAPIConfig.getInstance().updateCount(connection);
        				  
         			          				  
        	boolean ret = (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
        	if(ret)
        	{
        		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        		logger.debug(xml);
        		Response res = (Response)xstream.fromXML(xml);
        		return res.getWantslist().get(0);
        	}
        	else
        	{
        		throw new MkmNetworkException(connection.getResponseCode());
        	}
	}
	
	public boolean deleteWantList(Wantslist l) throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/wantslist/"+l.getIdWantslist();
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		String temp =MkmConstants.XML_HEADER+"<request><wantslist><idGame>1</idGame><name>"+l.getIdWantslist()+"</name></wantslist></request>";
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
        				  connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"DELETE")) ;
        				  connection.setDoOutput(true);
        				  connection.setRequestMethod("DELETE");
        				  connection.setRequestProperty( "charset", "utf-8");
        				  connection.connect() ;
        				  
        				  OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        				  out.write(temp);
        				  out.close();
         				  
        boolean ret = (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
		MkmAPIConfig.getInstance().updateCount(connection);
			    
        if(ret)
    	{
    		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
    		logger.debug(xml);
    	}
        else
        {
        	throw new MkmNetworkException(connection.getResponseCode());
        }
        
      	return ret;
	}
	
	public void loadItems(Wantslist wl) throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
    	
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean ret = (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
		if(!ret)
			throw new MkmNetworkException(connection.getResponseCode());
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
   		logger.debug(xml);
		Response res = (Response)xstream.fromXML(xml);
		
		if(isEmpty(res.getWantslist().get(0)))
		{
			wl.setItem(new ArrayList<WantItem>());
			return;
		}
		wl.setItem(res.getWantslist().get(0).getItem());
	}

	private boolean isEmpty(Wantslist wantslist) {
		return wantslist.getItem().get(0).getProduct()==null;
	}
	
	
	
	
}
