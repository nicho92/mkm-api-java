package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.api.mkm.modele.LightArticle;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Order;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class OrderService {
	private XStream xstream;

	public enum ACTOR { seller,buyer}
	public enum STATE { bought,paid,sent,received,lost,cancelled}
	
	
	public OrderService() {
		xstream = Tools.instNewXstream();
	 		xstream.addImplicitCollection(Response.class, "links", Link.class);
	 		xstream.addImplicitCollection(Response.class, "order", Order.class);
	 		xstream.addImplicitCollection(Order.class, "article", LightArticle.class);
	}


	public List<Order> listOrders(File f) throws IOException
	{
		String xml= Files.readString(f.toPath());
	    Response res = (Response)xstream.fromXML(xml);
        
         if(isEmpty(res.getOrder()))
         	return new ArrayList<>();
 
         return res.getOrder();
	}

	
	public List<Order> listOrders(ACTOR a, STATE s,Integer min) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/orders/:actor/:state";
			link=link.replace(":actor", a.name());
			link=link.replace(":state", s.name());
		
		if(min!=null)
			link+="/"+min;
		
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
	    Response res = (Response)xstream.fromXML(xml);
        
         if(isEmpty(res.getOrder()))
         	return new ArrayList<>();
 
         return res.getOrder();
	}
	
	public Order getOrderById(int id) throws IOException
	{
		 String link=MkmConstants.MKM_API_URL+"/order/"+id;
		 String xml= Tools.getXMLResponse(link, "GET", this.getClass());
         Response res = (Response)xstream.fromXML(xml);
         return res.getOrder().get(0);
	}
	
	public void putTrackingNumber(int idOrder , String number) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/order/"+idOrder+"/tracking";
		StringBuilder temp = new StringBuilder();

		temp.append(MkmConstants.XML_HEADER);
			temp.append("<request>");
				temp.append("<trackingNumber>").append(number).append("</trackingNumber>");
			temp.append("</request>");
		
		Tools.getXMLResponse(link, "PUT", this.getClass(), temp.toString());
			
	 	 
	}
	
	
	public boolean isEmpty(List<Order> orders)
	{
		return (orders.get(0).getIdOrder()==0);
		
	}
	
	
}
