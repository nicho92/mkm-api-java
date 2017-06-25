package org.api.mkm.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.User;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class AuthenticationServices {

	
	private String appToken;
	private String appSecret;
	private String accessToken;
	private String accessSecret;
	private XStream xstream;
	
	static final Logger logger = LogManager.getLogger(AuthenticationServices.class.getName());

	public AuthenticationServices(String accessSecret,String accessToken,String appSecret,String appToken) throws MkmException {
		this.accessSecret=accessSecret;
		this.accessToken=accessToken;
		this.appSecret=appSecret;
		this.appToken=appToken;
		
		
		if(accessSecret==null||accessToken==null||appSecret==null||appToken==null)
			throw new MkmException("API authentication field must be filled");
		
		
		xstream = new XStream(new StaxDriver());
		XStream.setupDefaultSecurity(xstream);
 		xstream.addPermission(AnyTypePermission.ANY);
 		xstream.alias("response", Response.class);
 		xstream.ignoreUnknownElements();
 		xstream.addImplicitCollection(Response.class,"links",Link.class);
		
	}
	
	public User getAuthenticatedUser() throws MkmException, IOException, MkmNetworkException 
	{
		String link="https://www.mkmapi.eu/ws/v2.0/account";
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
		boolean ret= (connection.getResponseCode()>=200 || connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
			               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
		Response res = (Response)xstream.fromXML(xml);
		return res.getAccount();
	
	}
	
	private Map<String,String> parseQueryString(String query)
	 {
	        Map<String,String> queryParameters = new TreeMap<String, String>();
	        
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
	 
	@Deprecated
	public String generateOAuthSignature(String link,String method) throws MkmException {

		try{
        String realm = link ;
        String oauth_version =  "1.0" ;
        String oauth_consumer_key = appToken ;
        String oauth_token = accessToken ;
        String oauth_signature_method = "HMAC-SHA1";
        String oauth_timestamp = ""+ (System.currentTimeMillis()/1000) ;
        String oauth_nonce = "" + System.currentTimeMillis() ;
        String encode ="UTF-8";
       
        String baseString = method+"&" + URLEncoder.encode(link,encode) + "&" ;
        
        String paramString = "oauth_consumer_key=" + URLEncoder.encode(oauth_consumer_key,encode) + "&" +
                             "oauth_nonce=" + URLEncoder.encode(oauth_nonce,encode) + "&" +
                             "oauth_signature_method=" + URLEncoder.encode(oauth_signature_method,encode) + "&" +
                             "oauth_timestamp=" + URLEncoder.encode(oauth_timestamp,encode) + "&" +
                             "oauth_token=" + URLEncoder.encode(oauth_token,encode) + "&" +
                             "oauth_version=" + URLEncoder.encode(oauth_version,encode) ;
        
        
        baseString += URLEncoder.encode(paramString,encode) ;
       
        String signingKey = URLEncoder.encode( appSecret,encode) + "&" + URLEncoder.encode(accessSecret,encode) ;
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(signingKey.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(baseString.getBytes());
        
        
        String oauth_signature = DatatypeConverter.printBase64Binary(digest); //Base64.encode(digest).trim() ;     
        
        String authorizationProperty = 
                "OAuth " +
                "realm=\"" + realm + "\", " + 
                "oauth_version=\"" + oauth_version + "\", " +
                "oauth_timestamp=\"" + oauth_timestamp + "\", " +
                "oauth_nonce=\"" + oauth_nonce + "\", " +
                "oauth_consumer_key=\"" + oauth_consumer_key + "\", " +
                "oauth_token=\"" + oauth_token + "\", " +
                "oauth_signature_method=\"" + oauth_signature_method + "\", " +
                "oauth_signature=\"" + oauth_signature + "\"" ;
        
        
        return authorizationProperty;
		}
		catch(InvalidKeyException e)
		{
			throw new MkmException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new MkmException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new MkmException(e.getMessage());
		}
	}
	    
	public String generateOAuthSignature2(String url,String method) throws MkmException{
	    	try{
	    		
	    	 Map<String,String> headerParams = new HashMap<String,String>();
	         Map<String,String> encodedParams = new TreeMap<String, String>();
	         int index = url.indexOf("?");
	         String signatureMethod = "HMAC-SHA1";
	         String version = "1.0";
	         String encode="UTF-8";
	         String nonce="" + System.currentTimeMillis();
	         String timestamp=""+ (System.currentTimeMillis()/1000);
	         String baseUri = (index>0?url.substring(0,index):url);
	         String signatureKey = URLEncoder.encode(appSecret,encode) + "&" + URLEncoder.encode(accessSecret,encode);
	         
	         headerParams = new TreeMap<String, String>();
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
	         
	         if (index > 0)
	         {
	             String urlParams = url.substring(index+1);
	             Map<String,String> args = parseQueryString(urlParams);

	             for (String k : args.keySet())
	            	 headerParams.put(k, args.get(k));
	         }
	         
	         for (String k : headerParams.keySet())
	             if (false == k.equalsIgnoreCase("realm"))
	                 encodedParams.put(URLEncoder.encode(k,encode), URLEncoder.encode(headerParams.get(k),encode));
	         
	         List<String> paramStrings = new ArrayList<String>();
	        
	         for(String parameter:encodedParams.keySet())
	         {
	        	 paramStrings.add(parameter + "=" + encodedParams.get(parameter));
	         }
	         
	         String paramString = URLEncoder.encode(Tools.join(paramStrings, "&"),encode);
	         
	         baseString += paramString;
	         
	         Mac mac = Mac.getInstance("HmacSHA1");
	         SecretKeySpec secret = new SecretKeySpec(signatureKey.getBytes(), mac.getAlgorithm());
	         mac.init(secret);
	         byte[] digest = mac.doFinal(baseString.getBytes());
	         String oAuthSignature = DatatypeConverter.printBase64Binary(digest);    
	         headerParams.put("oauth_signature", oAuthSignature);
	         
	         List<String> headerParamStrings = new ArrayList<String>();
	    
	         for(String parameter:headerParams.keySet())
	             headerParamStrings.add(parameter + "=\"" + headerParams.get(parameter) + "\"");
	         
	         String authHeader = "OAuth " + Tools.join(headerParamStrings,", ");
	     	return authHeader;
	    	}
	    	catch(Exception e)
	    	{
	    		throw new MkmException(e.getMessage());
	    	}
	    }

	    
	    
	   


	
}
