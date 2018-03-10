package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Localization;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.EncodingUtils;
import org.api.mkm.tools.IntConverter;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class ProductServices {

	private AuthenticationServices auth;
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
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class,"product", Product.class);
	 		xstream.addImplicitCollection(Response.class,"links",Link.class);
	 		xstream.addImplicitCollection(Response.class, "single",Product.class);
	 		xstream.addImplicitCollection(Response.class, "expansion",Expansion.class);
	 		xstream.addImplicitCollection(Product.class,"links",Link.class);
	 		xstream.addImplicitCollection(Product.class,"localization",Localization.class);
	 		xstream.addImplicitCollection(Product.class,"reprint",Expansion.class);
	 		xstream.registerConverter(new IntConverter());
	 		xstream.ignoreUnknownElements();
	}
	
	public void exportPriceGuide(File f) throws IOException
	{
		exportPriceGuide(f,null);
	}
	
	public void exportPriceGuide(File f,Integer idGame) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/priceguide";
	
		if(idGame!=null)
			link=MkmConstants.MKM_API_URL+"/priceguide?idGame="+idGame;
		
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
	    
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
	   	               
       boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
       if(!ret)
       {
    	   throw new MkmNetworkException(connection.getResponseCode());
       }
       MkmAPIConfig.getInstance().updateCount(connection);	      	 	 
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
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
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
	    
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
       boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
       if(!ret)
    	   throw new MkmNetworkException(connection.getResponseCode());	               
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
		
		
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
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
	    
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
        connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
        connection.connect() ;
        MkmAPIConfig.getInstance().updateCount(connection);
        boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	 if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
        
        String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
        logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
     	Response res = (Response)xstream.fromXML(xml);
		return res.getSingle();
		
	}
	
	public List<Product> findProduct(String name,Map<PRODUCT_ATTS,String> atts)throws IOException
	{
		xstream.aliasField("expansion", Product.class, "expansionName");
 		
		String link = MkmConstants.MKM_API_URL+"/products/find?search="+EncodingUtils.encodeString(name);
	
		if(atts!=null && atts.size()>0)
	    	{
				link+="&";
	    		List<String> paramStrings = new ArrayList<>();
	 	        for(Entry<PRODUCT_ATTS, String> parameter:atts.entrySet())
		             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
		        
	 	        link+=Tools.join(paramStrings, "&");
	    	}
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		MkmAPIConfig.getInstance().updateCount(connection);
        
        boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	 if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
        
    	String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
    	
    	logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
    	Response res = (Response)xstream.fromXML(xml);
		
    	if(isEmpty(res.getProduct()))
    		return new ArrayList<>();

	return res.getProduct();
	}
	
	public List<Product> findMetaProduct(String name,Map<PRODUCT_ATTS,String> atts)throws IOException
	{
		String link = MkmConstants.MKM_API_URL+"/products/find?search="+EncodingUtils.encodeString(name);
		if(atts.size()>0)
    	{
			link+="&";
    		List<String> paramStrings = new ArrayList<>();
 	        for(Entry<PRODUCT_ATTS, String> parameter:atts.entrySet())
	             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
	        
 	        link+=Tools.join(paramStrings, "&");
    	}
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               
		
        MkmAPIConfig.getInstance().updateCount(connection);
        
        boolean ret=(connection.getResponseCode()>=200 && connection.getResponseCode()<300);
       
        if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
        
    	String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
    	
    	logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
    	Response res = (Response)xstream.fromXML(xml);
    	return res.getProduct();
	}

	public Product getMetaProductById(int idMeta)throws IOException
	{
		xstream.aliasField("expansion", Product.class, "expansion"); //remove from V1.1 call
 		
    	String link = MkmConstants.MKM_API_URL+"/metaproducts/"+idMeta;
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
        
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
       boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
       if(!ret)
 		throw new MkmNetworkException(connection.getResponseCode());

		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
		Response res = (Response)xstream.fromXML(xml);
		return res.getProduct().get(0);
	}
	
	public Product getProductById(int idProduct) throws IOException
	{
		xstream.aliasField("expansion", Product.class, "expansion"); //remove from V1.1 call
 		
    	String link = MkmConstants.MKM_API_URL+"/products/"+idProduct;
    	logger.debug(MkmConstants.MKM_LOG_LINK+link);
        
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
       boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
       if(!ret)
    	   throw new MkmNetworkException(connection.getResponseCode());
       
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
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
	
	private boolean isEmpty(List<Product> product) {
		return product.get(0).getIdProduct()==0;
	}
	
}
