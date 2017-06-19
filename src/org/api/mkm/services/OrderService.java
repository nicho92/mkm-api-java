package org.api.mkm.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Game;
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

	public static enum ACTOR { seller,buyer};
	public static enum STATE { bought,paid,sent,received,lost,cancelled};
	
	
	
	
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

	
	public List<Order> listOrders(ACTOR a, STATE s,Integer min) throws InvalidKeyException, NoSuchAlgorithmException, MalformedURLException, IOException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/orders/:actor/:state";
			link=link.replaceAll(":actor", a.name());
			link=link.replaceAll(":state", s.name());
		
		if(min!=null)
			link+="/"+min;
		
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
         connection.connect() ;

         String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
         Response res = (Response)xstream.fromXML(xml);
         
         return res.getOrder();
	}
	
	public Order getOrderById(int id) throws MalformedURLException, IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/order/"+id;
		
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
         connection.connect() ;

         String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
       
         Response res = (Response)xstream.fromXML(xml);
     	
         return res.getOrder().get(0);
	}
	
	
}
