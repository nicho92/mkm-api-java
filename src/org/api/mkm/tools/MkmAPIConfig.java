package org.api.mkm.tools;

import org.api.mkm.services.AuthenticationServices;

public class MkmAPIConfig {

	private static MkmAPIConfig instance;
	
	AuthenticationServices auth;
	
	
	public void init( String accessSecret ,String accessToken ,String appSecret,String appToken)
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
	
	
	
}
