package org.api.mkm.services;

import java.io.IOException;
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
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Order;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmAPIConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class OrderService {
	private AuthenticationServices auth;
	private XStream xstream;

	public enum ACTOR { seller,buyer};
	public enum STATE { bought,paid,sent,received,lost,cancelled};
	
	static final Logger logger = LogManager.getLogger(OrderService.class.getName());
	
	
	
	public OrderService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class, "links", Link.class);
	 		xstream.addImplicitCollection(Response.class, "order", Order.class);
	 		xstream.addImplicitCollection(Order.class, "article", Article.class);
	 		xstream.ignoreUnknownElements();
	}

	
	public List<Order> listOrders(ACTOR a, STATE s,Integer min) throws IOException, MkmException, MkmNetworkException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/orders/:actor/:state";
			link=link.replaceAll(":actor", a.name());
			link=link.replaceAll(":state", s.name());
		
		if(min!=null)
			link+="/"+min;
		
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
        
         if(isEmpty(res.getOrder()))
         	return new ArrayList<>();
         
         for(Order o : res.getOrder())
        	 for(Article art : o.getArticle())
        		 art.getProduct().setIdProduct(art.getIdProduct());
         
         
         return res.getOrder();
	}
	
	public Order getOrderById(int id) throws IOException, MkmException, MkmNetworkException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/order/"+id;
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
     	
         return res.getOrder().get(0);
	}
	
	public boolean isEmpty(List<Order> orders)
	{
		return (orders.get(0).getIdOrder()==0);
		
	}
	
	
}
