package org.api.mkm.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class ArticleService {

	private XStream xstream;
	
	public ArticleService() {
			xstream = Tools.instNewXstream();
	 		xstream.addImplicitCollection(Response.class,"article", Article.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	}
	
	public List<Article> find(User u,Map<ARTICLES_ATT,String> atts) throws IOException 
	{
		String link = MkmConstants.MKM_API_URL+"/users/"+u.getUsername()+"/articles";
		if(atts!=null && atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<>();
	 	        for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
		 
         String xml= Tools.getXMLResponse(link, "GET",this.getClass());
         Response res = (Response)xstream.fromXML(xml);
    	return res.getArticle();
	}
	
	

	public List<Article> find(Product p,Map<ARTICLES_ATT,String> atts) throws IOException 
	{
		String link = MkmConstants.MKM_API_URL+"/articles/"+p.getIdProduct();
    	if(atts!=null && atts.size()>0)
	    	{
	    		link+="?";
	    		List<String> paramStrings = new ArrayList<>();
	    		 for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
    	
	    String xml= Tools.getXMLResponse(link, "GET",this.getClass());
		
	    try {
		    Response res = (Response)xstream.fromXML(xml);
			
			for(Article a : res.getArticle())
				a.setProduct(p);
			
			return res.getArticle();
		
	    }
	    catch(Exception e)
	    {
	    	return new ArrayList<>();
	    }
		
	}
	
}
