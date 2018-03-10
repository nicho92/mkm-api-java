package org.api.mkm.tools;

import java.net.HttpURLConnection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.services.AuthenticationServices;

public class MkmAPIConfig {

	private static MkmAPIConfig instance;
	private int maxCall=0;
	private int countCall;
	
	static final Logger logger = LogManager.getLogger(MkmAPIConfig.class.getName());

	AuthenticationServices auth;
	
	
	public void init( String accessSecret ,String accessToken ,String appSecret,String appToken) throws MkmException
	{
		auth=new AuthenticationServices(accessSecret, accessToken, appSecret, appToken);
	}
	
	public static MkmAPIConfig getInstance()
	{
		if(instance==null)
			instance=new MkmAPIConfig();
		return instance;
	}

		
	
	public AuthenticationServices getAuthenticator() {
		return auth;
	}
	
	public void updateCount(HttpURLConnection connection) 
	{
       String limit = connection.getHeaderField("X-Request-Limit-Max");
       String count = connection.getHeaderField("X-Request-Limit-Count");

       if(maxCall==0 && limit!=null)
    	      maxCall=Integer.parseInt(limit);
       
       
       if(count!=null)
    	   countCall=Integer.parseInt(count);
       
       
       logger.info("call : " + countCall+ "/" + maxCall);
       
 	}
	
	public int getCountCall() {
		return countCall;
	}
	
	public int getMaxCall() {
		return maxCall;
	}
}
