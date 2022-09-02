package org.api.mkm.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLTools {

	private static XMLTools inst;
	private  DocumentBuilder builder ;
	private XPath path;
	
	private XMLTools()
	{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
									 factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
									 factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant

		try {
			
			XPathFactory xpf = XPathFactory.newInstance();
			 path = xpf.newXPath();
			
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LogManager.getLogger(this.getClass()).error(e);
		}
	}
	
	public Document toDoc(String xml) throws SAXException, IOException
	{
		return builder.parse(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public List<String> getElementAt(String xml, String xpath) throws SAXException, IOException, XPathExpressionException
	{
		Document d = toDoc(xml);
		NodeList list = (NodeList)path.compile(xpath).evaluate(d,XPathConstants.NODESET);
				
		List<String> ret = new ArrayList<>();
		for(int i =0;i<list.getLength();i++)
		{
			ret.add(list.item(i).getTextContent());
		}
		return ret;
	}
	
	
	
	public static XMLTools inst()
	{
		if(inst==null)
			inst= new XMLTools();
		
		return inst;
	}
	
	
	
}
