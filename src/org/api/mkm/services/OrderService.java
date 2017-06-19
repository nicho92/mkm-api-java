package org.api.mkm.services;

import java.util.List;

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
	 		
	 		
	 		xstream.ignoreUnknownElements();
	}

	
	public List<Order> listOrders(ACTOR a, STATE s,Integer min)
	{
		String link="https://www.mkmapi.eu/ws/v2.0/orders/:actor/:state";
			link=link.replaceAll(":actor", a.name());
			link=link.replaceAll(":state", s.name());
		
		if(min!=null)
			link+="/"+min;
		
		return null;
	}
	
	public Order getOrderById(int id)
	{
		String link="https://www.mkmapi.eu/ws/v2.0/order/"+id;
		
		return null;
	}
	
	
}
