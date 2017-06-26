package org.api.mkm.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Basket;
import org.api.mkm.modele.ShoppingCart;
import org.api.mkm.tools.MkmAPIConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class CartServices {

	private AuthenticationServices auth;
	private XStream xstream;
	static final Logger logger = LogManager.getLogger(CartServices.class.getName());

	public CartServices() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Basket.class);
	 		xstream.addImplicitCollection(Basket.class,"shoppingCart",ShoppingCart.class);
	 		xstream.addImplicitCollection(ShoppingCart.class, "article", Article.class);
	 		xstream.ignoreUnknownElements();
	}
	
	public boolean addArticle(Article a) throws IOException, MkmException, MkmNetworkException
	{
		List<Article> list = new ArrayList<Article>();
		list.add(a);
		return addArticles(list);
	}
	
	
	public boolean addArticles(List<Article> articles) throws IOException, MkmException, MkmNetworkException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/shoppingcart";
		logger.debug("LINK="+link);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"PUT")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.connect();
	
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

		StringBuffer temp = new StringBuffer();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request><action>add</action>");

		for(Article a : articles)
		{
			temp.append("<article>");
			temp.append("<idArticle>"+a.getIdArticle()+"</idArticle>");
			temp.append("<amount>"+a.getCount()+"</amount>");
			temp.append("</article>");
		}
		temp.append("</request>");
		
		logger.debug("REQU="+temp);
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
		
		
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		logger.debug("ADDITEM "+xml);
		
		return ret;
	}
	public boolean empty() throws IOException, MkmException, MkmNetworkException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/shoppingcart";
		logger.debug("LINK="+link);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"DELETE")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("DELETE");
		connection.connect();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());

		return ret;
	}
	
	
	public boolean removeArticles(List<Article> articles) throws IOException, MkmException, MkmNetworkException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/shoppingcart";
		logger.debug("LINK="+link);
		
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"PUT")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.connect();
		
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

		StringBuffer temp = new StringBuffer();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request><action>remove</action>");

		for(Article a : articles)
		{
			temp.append("<article>");
			temp.append("<idArticle>"+a.getIdArticle()+"</idArticle>");
			temp.append("<amount>"+a.getCount()+"</amount>");
			temp.append("</article>");
		}
		temp.append("</request>");
		logger.debug("REQU="+temp);
		
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);

		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());

		
		return ret;
	}
	
	public Basket getBasket() throws IOException, MkmException, MkmNetworkException
	{
		String link = "https://www.mkmapi.eu/ws/v2.0/shoppingcart";
		logger.debug("LINK="+link);
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
   		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
           
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		Basket res = (Basket)xstream.fromXML(xml);
		return res;
	}
	
	
	
	
	
	
}
