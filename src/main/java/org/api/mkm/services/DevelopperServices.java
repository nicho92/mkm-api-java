package org.api.mkm.services;

import java.io.IOException;

import org.api.mkm.tools.Tools;

public class DevelopperServices {

	public String execute(String link,String content,String method) throws IOException
	{
		if(content!=null)
			return Tools.getXMLResponse(link, method, this.getClass(), content);
		else
			return Tools.getXMLResponse(link, method, this.getClass());
	}
	
}
