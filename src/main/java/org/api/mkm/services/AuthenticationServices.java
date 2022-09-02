package org.api.mkm.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class AuthenticationServices {

	
	private String appToken;
	private String appSecret;
	private String accessToken;
	private String accessSecret;
	private XStream xstream;
	private Logger logger = LogManager.getLogger(this.getClass());


	public AuthenticationServices(String accessSecret,String accessToken,String appSecret,String appToken) throws MkmException {
		this.accessSecret=accessSecret;
		this.accessToken=accessToken;
		this.appSecret=appSecret;
		this.appToken=appToken;
		
		
		if(accessSecret==null||accessToken==null||appSecret==null||appToken==null)
			throw new MkmException("API authentication field must be filled");
		
		if(accessSecret.equals("")||accessToken.equals("")||appSecret.equals("")||appToken.equals(""))
			throw new MkmException("API authentication field must be filled");
		
		
		xstream = Tools.instNewXstream();
 		xstream.addImplicitCollection(Response.class,"links",Link.class);
		
	}
	
	public User getAuthenticatedUser() throws IOException 
	{
		String link=MkmConstants.MKM_API_URL+"/account";
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		logger.debug(MkmConstants.MKM_LOG_LINK+link);
        MkmAPIConfig.getInstance().updateCount(connection);
    	               
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
			               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
		Response res = (Response)xstream.fromXML(xml);
		return res.getAccount();
	
	}
	
	private Map<String,String> parseQueryString(String query)
	 {
	        Map<String,String> queryParameters = new TreeMap<>();
	         String[] querySegments = query.split("&");
	        for (String segment : querySegments)
	        {
	            String[] parts = segment.split("=");
	            if (parts.length > 0)
	            {
	                String key = parts[0].replaceAll("\\?", " ").trim();
	                String val = parts[1].trim();
	                queryParameters.put(key, val);
	            }
	        }
	        return queryParameters;
	 }

	public String generateOAuthSignature2(String url,String method) throws MkmException{
	    	try{
	    	 Map<String,String> headerParams;
	         Map<String,String> encodedParams = new TreeMap<>();
	         int index = url.indexOf('?');
	         String signatureMethod = "HMAC-SHA1";
	         String version = "1.0";
	         String encode="UTF-8";
	         String nonce="" + System.currentTimeMillis();
	         String timestamp=""+ (System.currentTimeMillis()/1000);
	         String baseUri = (index>0?url.substring(0,index):url);
	         String signatureKey = URLEncoder.encode(appSecret,encode) + "&" + URLEncoder.encode(accessSecret,encode);
	         
	         headerParams = new TreeMap<>();
	         headerParams.put("oauth_consumer_key", appToken);
	         headerParams.put("oauth_token", accessToken);
	         headerParams.put("oauth_nonce", nonce);
	         headerParams.put("oauth_timestamp", timestamp);
	         headerParams.put("oauth_signature_method", signatureMethod);
	         headerParams.put("oauth_version", version);
	         headerParams.put("realm", baseUri);
	         
	         
	         String baseString = method.toUpperCase()
	                 + "&"
	                 + URLEncoder.encode(baseUri, encode)
	                 + "&";
	         
	         logger.trace("baseString="+baseString);
	         
	         if (index > 0)
	         {
	             String urlParams = url.substring(index+1);
	             Map<String,String> args = parseQueryString(urlParams);

	             for (Entry<String, String> k : args.entrySet())
	             {
	            	 headerParams.put(k.getKey(), k.getValue());
	            	 logger.trace("headerParams.put("+k.getKey()+","+k.getValue()+")");
	             }
	         }
	         
	         for (Entry<String, String> k : headerParams.entrySet())
	             if (!k.getKey().equalsIgnoreCase("realm"))
	             {
	            	 encodedParams.put(URLEncoder.encode(k.getKey(),encode), k.getValue());
	            	 logger.trace("encodedParams.put("+URLEncoder.encode(k.getKey(),encode)+","+k.getValue()+")");
	             }
	            
	         List<String> paramStrings = new ArrayList<>();
	        
	         for(Entry<String, String> parameter:encodedParams.entrySet())
	         {
	        	 paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
	        	 logger.trace("paramStrings.add("+parameter.getKey()+"="+parameter.getValue()+")");
	         }
	         
	         String paramString = URLEncoder.encode(Tools.join(paramStrings, "&"),encode).replaceAll("'", "%27");
	         
	         logger.trace("paramString="+paramString);
	         baseString += paramString;
	         
	         Mac mac = Mac.getInstance("HmacSHA1");
	         SecretKeySpec secret = new SecretKeySpec(signatureKey.getBytes(), mac.getAlgorithm());
	         mac.init(secret);
	         byte[] digest = mac.doFinal(baseString.getBytes());
	         String oAuthSignature = DatatypeConverter.printBase64Binary(digest);    
	         headerParams.put("oauth_signature", oAuthSignature);
	         
	         List<String> headerParamStrings = new ArrayList<>();
	    
	         for(Entry<String, String> parameter:headerParams.entrySet())
	             headerParamStrings.add(parameter.getKey() + "=\"" + parameter.getValue() + "\"");
	         
	         String authHeader = "OAuth " + Tools.join(headerParamStrings,", ");
	         logger.debug("authHeader="+authHeader);
	     	return authHeader;
	    	}
	    	catch(Exception e)
	    	{
	    		throw new MkmException(e);
	    	}
	    }

	    
	    
	   


	
}
