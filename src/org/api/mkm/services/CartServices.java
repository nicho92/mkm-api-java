package org.api.mkm.services;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Basket;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.ShoppingCart;
import org.api.mkm.modele.WantItem;
import org.api.mkm.tools.MkmAPIConfig;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class CartServices {

	private AuthenticationServices auth;
	private XStream xstream;
	
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
	
	public boolean addArticles(List<Article> articles) throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/shoppingcart";
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
		out.write(temp.toString());
		out.close();
		boolean ret= (connection.getResponseCode()>=200 || connection.getResponseCode()<300);
		return ret;
	}
	public boolean empty() throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/shoppingcart";
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"DELETE")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("DELETE");
		connection.connect();
		boolean ret= (connection.getResponseCode()>=200 || connection.getResponseCode()<300);
		return ret;
	}
	
	
	public boolean removeArticles(List<Article> articles) throws IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/shoppingcart";
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
		out.write(temp.toString());
		out.close();
		boolean ret= (connection.getResponseCode()>=200 || connection.getResponseCode()<300);
		return ret;
	}
	
	public Basket getBasket() throws MalformedURLException, IOException, InvalidKeyException, NoSuchAlgorithmException
	{
		String link = "https://www.mkmapi.eu/ws/v2.0/shoppingcart";
    	
    	HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		Basket res = (Basket)xstream.fromXML(xml);
		return res;
	}
	
	
	
	
	
	
}
