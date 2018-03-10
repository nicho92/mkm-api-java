package org.api.mkm.tools;

public class EncodingUtils {

	private EncodingUtils()
	{
		
	}
	
	public static String encodeString(String s)
	{
		return s.replaceAll(" ","%20")
				.replaceAll("'", "%27")
				.replaceAll(":", "%3A")
				.replaceAll(",","%2C")
				.replaceAll("&", "%26")
				.replaceAll("\"", "%22")
				.replaceAll("\u00C6", "Ae");
	}
}
