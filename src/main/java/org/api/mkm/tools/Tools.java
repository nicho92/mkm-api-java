package org.api.mkm.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Localization;
import org.api.mkm.modele.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;



public class Tools {
	
   private static final byte[] BUFFER_SIZE = new byte[256];

   private Tools()
   {
	   
   }
   
   public static void main(String[] args) {
	Tools.listLanguages();
}
   
   public static List<Localization> listLanguages()
   {
	   var ret = new ArrayList<Localization>();
	   var counter = new AtomicInteger();
	   List.of("English","French","German","Spanish","Italian","Simplified Chinese","Japanese","Portuguese","Russian","Korean","Traditional Chinese").forEach(s->{
		   Localization l = new Localization(counter.getAndIncrement(), s);
		   l.setLanguageName(s);
		   ret.add(l);
		});
	   
	   return ret;
   }
   
   
   
   
   public static String encodeString(String s)
	{
		return s.replace(" ","%20")
				.replace("'", "%27")
				.replace(":", "%3A")
				.replace(",","%2C")
				.replace("&", "%26")
				.replace("\"", "%22")
				.replace("\u00C6", "Ae");
	}
   
   public static XStream instNewXstream()
   {
   	XStream xstream = new XStream(new StaxDriver());
		XStream.setupDefaultSecurity(xstream);
		xstream.ignoreUnknownElements();
		xstream.addPermission(AnyTypePermission.ANY);
		xstream.alias("response", Response.class);
 		xstream.registerConverter(new IntConverter());
 		xstream.registerConverter(new MkmBooleanConverter());

		return xstream;
		
   }
  
   public static void unzip(File zipFilePath,File to) throws IOException {
    	try(GZIPInputStream zipIn = new GZIPInputStream(new FileInputStream(zipFilePath));BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(to)))
    	{
            int read = 0;
            while ((read = zipIn.read(BUFFER_SIZE)) != -1) 
               bos.write(BUFFER_SIZE, 0, read);
    	}
    }
	
    
    public static String join(Collection<?> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator<?> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
              break;                  
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }
    
    
    public static Document getDocument(String url) throws IOException
    {
    	return Jsoup.parse(new URL(url), 0);
    }
   
    
    public static String getXMLResponse(String link,String method, @SuppressWarnings("rawtypes") Class serv) throws IOException {
    	 LogManager.getLogger(serv).debug(MkmConstants.MKM_LOG_LINK+link);
    	 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, MkmAPIConfig.getInstance().getAuthenticator().generateOAuthSignature2(link,method)) ;
         connection.setRequestMethod(method);
         connection.setDoOutput(true);
         connection.setRequestProperty("charset", "utf-8");
         connection.connect() ;
        
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
		if(!ret)
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		MkmAPIConfig.getInstance().updateCount(connection);	      
		String xml = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		LogManager.getLogger(serv).debug(MkmConstants.MKM_LOG_RESPONSE+xml);
		
		return xml;
     }
    
    public static String getXMLResponse(String link,String method, @SuppressWarnings("rawtypes") Class serv,String content) throws IOException {
   	 	LogManager.getLogger(serv).debug(method + " "+ MkmConstants.MKM_LOG_LINK+link);
		   	 	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		        connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, MkmAPIConfig.getInstance().getAuthenticator().generateOAuthSignature2(link,method)) ;
		        connection.setRequestMethod(method);
		        connection.setDoOutput(true);
		        connection.connect() ;
        
        LogManager.getLogger(serv).debug(MkmConstants.MKM_LOG_REQUEST+content);
        
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(content);
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
        
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
		if(!ret)
		{
			throw new MkmNetworkException(connection.getResponseCode());
		}
		
		MkmAPIConfig.getInstance().updateCount(connection);	      
		String xml = IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		LogManager.getLogger(serv).debug(MkmConstants.MKM_LOG_RESPONSE+xml);
		return xml;
    }
    
  
    
}
