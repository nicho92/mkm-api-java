package org.api.mkm.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.api.mkm.exceptions.MkmException;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Localization;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class WantsService {

	private XStream xstream;
	
	
	public WantsService() {
		
		xstream = Tools.instNewXstream();
	 		xstream.addImplicitCollection(Response.class,"wantslist", Wantslist.class);
	 		xstream.addImplicitCollection(Wantslist.class,"item", WantItem.class);
	 		xstream.addImplicitCollection(Wantslist.class,"links", Link.class);
	 		xstream.addImplicitCollection(Product.class,"localization",Localization.class);
	 		xstream.addImplicitCollection(WantItem.class,"idLanguage",Integer.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
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
		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>deleteItem</action>");
		for(WantItem w : list)
		{
			temp.append("<want>");
			temp.append("<idWant>"+w.getIdWant()+"</idWant>");
		}		    
		temp.append("</want></request>");
		
		String xml= Tools.getXMLResponse(link, "PUT", getClass(), temp.toString());
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
	
	public void updateItem(Wantslist wl,WantItem it) throws IOException
	{
		
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
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

		String xml= Tools.getXMLResponse(link, "PUT", getClass(), temp.toString());
		
		Response res = (Response)xstream.fromXML(xml);
		if(res.getErrors()!=null)
			throw new MkmException(res.getErrors());
		
	}
	
	public List<Wantslist> getWantList() throws IOException
	{
    	String link = MkmConstants.MKM_API_URL+"/wantslist";
    	String xml= Tools.getXMLResponse(link, "GET", this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		return res.getWantslist();
	}
	
	public void addItem(Wantslist wl, WantItem item) throws IOException
	{
		addItem(wl, List.of(item));
	}
	
	public void addItem(Wantslist wl, List<WantItem> items) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();

		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>addItem</action>");
			for(WantItem w : items)
			{
				temp.append("<product>");
					temp.append("<idProduct>"+w.getIdProduct()+"</idProduct>");
					temp.append("<count>"+w.getCount()+"</count>");
					temp.append("<minCondition>"+w.getMinCondition()+"</minCondition>");
			
					if(w.isMailAlert()!=null)
						temp.append("<mailAlert>"+w.isMailAlert()+"</mailAlert>");
					else
						temp.append("<mailAlert/>");
					
					if(!w.getIdLanguage().isEmpty())
						for(Integer i : w.getIdLanguage())
							temp.append("<idLanguage>"+i+"</idLanguage>");
		
		
					if(w.getWishPrice()>0)
						temp.append("<wishPrice>"+w.getWishPrice()+"</wishPrice>");
					else
						temp.append("<wishPrice/>");
					
					if(w.isPlayset()!=null)
							temp.append("<isPlayset>"+w.isPlayset()+"</isPlayset>");
	
				temp.append("</product>");
			}		    
		temp.append("</request>");
		
		Tools.getXMLResponse(link, "PUT", this.getClass(), temp.toString());
		
	}
	
	public void renameWantList(Wantslist wl , String name) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request><action>editWantslist</action>");
		temp.append("<name>").append(name).append("</name></request>");
		Tools.getXMLResponse(link, "PUT", this.getClass(), temp.toString());
	}
	
	public Wantslist createWantList(String name) throws IOException
	{
		
		if(name.length()>30)
			name=name.substring(0, 30);
		
		String link = MkmConstants.MKM_API_URL+"/wantslist";
		String temp = MkmConstants.XML_HEADER+"<request><wantslist><idGame>1</idGame><name>"+name+"</name></wantslist></request>";
		String xml = Tools.getXMLResponse(link, "POST", this.getClass(),temp);
        Response res = (Response)xstream.fromXML(xml);
   		return res.getWantslist().get(0);
	}
	
	public void deleteWantList(Wantslist l) throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/wantslist/"+l.getIdWantslist();
		String temp =MkmConstants.XML_HEADER+"<request><wantslist><idGame>1</idGame><name>"+l.getIdWantslist()+"</name></wantslist></request>";
		Tools.getXMLResponse(link, "DELETE", this.getClass(), temp);
	}
	
	public void loadItems(Wantslist wl) throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/wantslist/"+wl.getIdWantslist();
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
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
