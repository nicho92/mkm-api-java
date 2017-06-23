package org.api.mkm.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class ArticleService {

	private AuthenticationServices auth;
	private XStream xstream;
	static final Logger logger = LogManager.getLogger(ArticleService.class.getName());

	
	public ArticleService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"article", Article.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.ignoreUnknownElements();
	}
	
	public List<Article> find(User u,Map<ARTICLES_ATT,String> atts) throws InvalidKeyException, NoSuchAlgorithmException, IOException
	{
		String link = "https://www.mkmapi.eu/ws/v2.0/users/"+u.getUsername()+"/articles";
		logger.debug("LINK="+link);
		
		if(atts!=null)
			if(atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<String>();
	 	        for(ARTICLES_ATT parameter:atts.keySet())
		             paramStrings.add(parameter + "=" + atts.get(parameter));
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
		
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
         connection.connect() ;
         String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
         logger.debug("RESP="+xml);
  	   
         Response res = (Response)xstream.fromXML(xml);
		
     	if(isEmpty(res.getArticle()))
    		return new ArrayList<Article>();
    
         
         
		return res.getArticle();
	}
	
	
	private boolean isEmpty(List<Article> article) {
		
		return (article.get(0).getIdArticle()==0);
	}

	public List<Article> find(Product p,Map<ARTICLES_ATT,String> atts) throws InvalidKeyException, NoSuchAlgorithmException, IOException
	{
    	String link = "https://www.mkmapi.eu/ws/v2.0/articles/"+p.getIdProduct();
    	logger.debug("LINK="+link);
    	
    	if(atts!=null)
	    	if(atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<String>();
	 	        for(ARTICLES_ATT parameter:atts.keySet())
		             paramStrings.add(parameter + "=" + atts.get(parameter));
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
    	logger.debug("LINK="+link);
    	
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug("RESP="+xml);
		  	  
		Response res = (Response)xstream.fromXML(xml);
		
		for(Article a : res.getArticle())
			a.setProduct(p);
		
		return res.getArticle();
	}
	
}
