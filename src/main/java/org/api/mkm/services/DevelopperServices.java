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
import org.api.mkm.tools.Tools;

public class DevelopperServices {

	public String execute(String link,String content,String method) throws IOException
	{
		if(content!=null)
			return Tools.postXMLResponse(link, method, this.getClass(), content);
		else
			return Tools.getXMLResponse(link, method, this.getClass());
	}
	
}
