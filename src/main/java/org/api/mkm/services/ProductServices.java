package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Game;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Localization;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;
import org.jsoup.nodes.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.thoughtworks.xstream.XStream;

public class ProductServices {

	private XStream xstream;
	private Logger logger = LogManager.getLogger(this.getClass());
	public static final String ENGLISH="1";
	public static final String FRENCH="2";
	public static final String GERMAN="3";
	public static final String SPANISH="4";
	public static final String ITALIAN="5";
	
	public static String[] getLangs()
	{
		return new String[]{"ENGLISH","FRENCH","GERMAN","SPANISH","ITALIAN"};
	}
	
	public ProductServices() {
		
		xstream = Tools.instNewXstream();
	 		xstream.addImplicitCollection(Response.class,"product", Product.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.addImplicitCollection(Response.class, "single",Product.class);
	 		xstream.addImplicitCollection(Response.class, "expansion",Expansion.class);
	 		xstream.addImplicitCollection(Product.class,"links",Link.class);
	 		xstream.addImplicitCollection(Product.class,"localization",Localization.class);
	 		xstream.addImplicitCollection(Product.class,"reprint",Expansion.class);
	}
	
	public void exportPriceGuide(File f,Game game) throws IOException
	{
		exportPriceGuide(f, game.getIdGame());
	}
	
	
	public void exportPriceGuide(File f,Integer idGame) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/priceguide";
	
		if(idGame!=null)
			link=MkmConstants.MKM_API_URL+"/priceguide?idGame="+idGame;
		
			 	 
		String xml= Tools.getXMLResponse(link, "GET",this.getClass());
		Response res = (Response)xstream.fromXML(xml);
	
		byte[] bytes = Base64.decodeBase64(res.getPriceguidefile());
		File temp =  new File("mkm_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		if(!temp.delete())
		{
			logger.error("couln't remove " + temp.getAbsolutePath());
		}
	}
	
	
	public void exportProductList(File f) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/productlist";
		String xml= Tools.getXMLResponse(link, "GET",this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		
		
		byte[] bytes = Base64.decodeBase64(res.getProductsfile());
		File temp =  new File("mkm_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		if(!temp.delete())
		{
			logger.error("Couldn't remove "+temp.getAbsolutePath());
		}
		
		
	}
	
	public List<Product> getProductByExpansion(Expansion e)throws IOException 
	{
		String link=MkmConstants.MKM_API_URL+"/expansions/"+e.getIdExpansion()+"/singles";
	    String xml= Tools.getXMLResponse(link, "GET",this.getClass());
        logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
     	Response res = (Response)xstream.fromXML(xml);
		return res.getSingle();
		
	}
	
	public List<Product> findProduct(String name,Map<PRODUCT_ATTS,String> atts)throws IOException
	{
		xstream.aliasField("expansion", Product.class, "expansionName");
 		
		String link = MkmConstants.MKM_API_URL+"/products/find?search="+Tools.encodeString(name);
	
		if(atts!=null && atts.size()>0)
	    	{
				link+="&";
	    		List<String> paramStrings = new ArrayList<>();
	 	        for(Entry<PRODUCT_ATTS, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
		
		String xml= Tools.getXMLResponse(link, "GET",this.getClass());
    	Response res = (Response)xstream.fromXML(xml);
		
    	if(isEmpty(res.getProduct()))
    		return new ArrayList<>();

	return res.getProduct();
	}
	
	public List<Product> findMetaProduct(String name,Map<PRODUCT_ATTS,String> atts)throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/products/find?search="+Tools.encodeString(name);
		if(atts.size()>0)
    	{
			link+="&";
    		List<String> paramStrings = new ArrayList<>();
 	        for(Entry<PRODUCT_ATTS, String> parameter:atts.entrySet())
	             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
	        
 	        link+=Tools.join(paramStrings, "&");
    	}
		String xml= Tools.getXMLResponse(link, "GET",this.getClass());
    	Response res = (Response)xstream.fromXML(xml);
    	return res.getProduct();
	}

	public Product getMetaProductById(int idMeta)throws IOException
	{
		xstream.aliasField("expansion", Product.class, "expansion"); //remove from V1.1 call
 		String xml= Tools.getXMLResponse(MkmConstants.MKM_API_URL+"/metaproducts/"+idMeta, "GET",this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		return res.getProduct().get(0);
	}
	
	public Product getProductById(int idProduct) throws IOException
	{
		xstream.aliasField("expansion", Product.class, "expansion"); //remove from V1.1 call
		String xml= Tools.getXMLResponse(MkmConstants.MKM_API_URL+"/products/"+idProduct, "GET",this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		return res.getProduct().get(0);
	}
	
	public void fusion(Product from, Product dest)
	{
		 try {
			 for (Map.Entry<String, Object> e : PropertyUtils.describe(from).entrySet()) 
			 	 if (e.getValue() != null && !e.getKey().equals("class"))
	             		PropertyUtils.setProperty(dest, e.getKey(), e.getValue());
		} catch (Exception e1) {
			logger.error(e1);
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		new ProductServices().priceHistory(new Product(),false);
	}
	
	
	public Map<Date,Double> priceHistory(Product p,boolean foil) throws IOException
	{
		Map<Date,Double> ret = new HashMap<>();
			String url=MkmConstants.MKM_SITE_URL+"/"+p.getWebsite()+"?isFoil="+(foil?"Y":"N");
			Element d = Tools.getDocument(url).select("script.chart-init-script").first();
			
			String toParse = d.html();
			toParse = toParse.substring(toParse.indexOf("{\"type\""),toParse.indexOf("\"options\"")-1)+"}";
			JsonElement el = JsonParser.parseString(toParse);
			
			JsonArray dates = el.getAsJsonObject().get("data").getAsJsonObject().get("labels").getAsJsonArray();
			JsonArray values = el.getAsJsonObject().get("data").getAsJsonObject().get("datasets").getAsJsonArray().get(0).getAsJsonObject().get("data").getAsJsonArray();
			
			for(int i=0;i<dates.size();i++)
			{
				try {
					ret.put(new SimpleDateFormat("dd.MM.yyyy").parse(dates.get(i).getAsString()), values.get(i).getAsDouble());
				}
				catch(IndexOutOfBoundsException ioobe)
				{
					//do nothing
				} catch (ParseException e) {
					logger.error("Error parsing " + dates.get(i).getAsString(),e);
				}
			}
			return ret;
	}
	
	
	private boolean isEmpty(List<Product> product) {
		return product.get(0).getIdProduct()==0;
	}
	
}
