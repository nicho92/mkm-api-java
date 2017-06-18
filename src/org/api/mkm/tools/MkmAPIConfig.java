package org.api.mkm.tools;

import org.api.mkm.services.AuthenticationServices;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

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
