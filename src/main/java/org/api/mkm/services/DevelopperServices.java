package org.api.mkm.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.MkmConstants;

public class DevelopperServices {

	private Logger logger = LogManager.getLogger(this.getClass());
	private AuthenticationServices auth;

	public DevelopperServices() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
	}
	
	
	public void execute(String link,String content,String method) throws IOException
	{
		 HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
         					connection.addRequestProperty(MkmConstants.OAUTH_AUTHORIZATION_HEADER, auth.generateOAuthSignature2(link,method)) ;
         					connection.setRequestMethod(method);
         
         if(content!=null)
        	 connection.setDoOutput(true);
         
         connection.connect();
         
         if(content!=null) {
	         
	         OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	         out.write(content);
	 		 out.close();
         }
         
         
         MkmAPIConfig.getInstance().updateCount(connection);
         boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
         logger.debug("ret ="+ret);
         
      	 String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
         logger.debug(MkmConstants.MKM_LOG_RESPONSE+xml);
      	
         
	}
	
}
