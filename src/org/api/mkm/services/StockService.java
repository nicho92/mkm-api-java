package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.exceptions.MkmException;
import org.api.mkm.exceptions.MkmNetworkException;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Game;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmAPIConfig;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class StockService {

	private AuthenticationServices auth;
	private XStream xstream;
	static final Logger logger = LogManager.getLogger(StockService.class.getName());

	public StockService() {
		auth=MkmAPIConfig.getInstance().getAuthenticator();
		xstream = new XStream(new StaxDriver());
			XStream.setupDefaultSecurity(xstream);
	 		xstream.addPermission(AnyTypePermission.ANY);
	 		xstream.alias("response", Response.class);
	 		xstream.addImplicitCollection(Response.class, "links", Link.class);
	 		xstream.addImplicitCollection(Response.class, "article", Article.class);
	 		xstream.ignoreUnknownElements();
	}
	
	public List<Article> getStock(int idGame,String name) throws IOException, MkmException, MkmNetworkException
	{
		Game g = new Game();
		g.setIdGame(idGame);
		return getStock(g, name);
	}
	
	public List<Article> getStock() throws IOException, MkmException, MkmNetworkException
	{
		return getStock(null, null);
	}
	
	public List<Article> getStock(Game game,String name) throws IOException, MkmException, MkmNetworkException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/stock";
		logger.debug("LINK="+link);
		
		if(name!=null)
			link=link+"/"+URLEncoder.encode(name, "UTF-8");
		
		if(game!=null)
			link=link+"/"+game.getIdGame();
		
		
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
       boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
       if(!ret)
    	   throw new MkmNetworkException(connection.getResponseCode());
       
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		Response res = (Response)xstream.fromXML(xml);
		return res.getArticle();
	}
	
	public boolean addArticle(Article a) throws IOException, MkmException, MkmNetworkException
	{
		ArrayList<Article> list = new ArrayList<Article>();
		list.add(a);
		return addArticles(list);
	}
	
	public boolean addArticles(List<Article> list) throws IOException, MkmException, MkmNetworkException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/stock";
		logger.debug("LINK="+link);
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"POST")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.connect();
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

		StringBuffer temp = new StringBuffer();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request>");

		for(Article a : list)
		{
			temp.append("<article>");
				temp.append("<idProduct>").append(a.getIdProduct()).append("</idProduct>");
				temp.append("<count>").append(a.getCount()).append("</count>");
				if(a.getLanguage()!=null)
					temp.append("<idLanguage>").append(a.getLanguage().getIdLanguage()).append("</idLanguage>");
				
				temp.append("<price>").append(a.getPrice()).append("</price>");
				temp.append("<condition>").append(a.getCondition()).append("</condition>");
				temp.append("<isFoil>").append(a.isFoil()).append("</isFoil>");
				temp.append("<isSigned>").append(a.isSigned()).append("</isSigned>");
				temp.append("<isPlayset>").append(a.isPlayset()).append("</isPlayset>");
			temp.append("</article>");
		}		    
		temp.append("</request>");
		logger.debug("REQ="+temp);
		   
		
		out.write(temp.toString());
		out.close();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	 if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
		

		return ret;
	}
	
	public boolean removeArticle(Article a) throws IOException, MkmException, MkmNetworkException
	{
		ArrayList<Article> list = new ArrayList<Article>();
		list.add(a);
		return removeArticles(list);
	}
	
	public boolean removeArticles(List<Article> list) throws IOException, MkmException, MkmNetworkException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/stock";
		logger.debug("LINK="+link);
	    
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"DELETE")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("DELETE");
		connection.connect();
		MkmAPIConfig.getInstance().updateCount(connection);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

		StringBuffer temp = new StringBuffer();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request>");

		for(Article a : list)
		{
			temp.append("<article>");
				temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
				temp.append("<count>").append(a.getCount()).append("</count>");
			temp.append("</article>");
		}		    
		temp.append("</request>");
		out.write(temp.toString());
		out.close();
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	 if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
		

		return ret;
	}
	
	public void exportStockFile(File f) throws IOException, MkmException, MkmNetworkException
	{
		exportStockFile(f,null);
	}
	
	public void exportStockFile(File f,Map<ARTICLES_ATT,String> atts) throws IOException, MkmException, MkmNetworkException
	{
		String link="https://www.mkmapi.eu/ws/v2.0/stock/file";
		
		if(atts!=null)
    	{
    		link+="?";
    		List<String> paramStrings = new ArrayList<String>();
 	        for(ARTICLES_ATT parameter:atts.keySet())
	             paramStrings.add(parameter + "=" + atts.get(parameter));
	        
 	        link+=Tools.join(paramStrings, "&");
    	}
		logger.debug("LINK="+link);
	    
		
	    HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
			               connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"GET")) ;
			               connection.connect() ;
			               MkmAPIConfig.getInstance().updateCount(connection);
			               
   		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
   		if(!ret)
   			throw new MkmNetworkException(connection.getResponseCode());
	

			               
		String xml= IOUtils.toString(connection.getInputStream(), StandardCharsets.UTF_8);
		
		Response res = (Response)xstream.fromXML(xml);
		
		
		byte[] bytes = Base64.decodeBase64(res.getStock());
		File temp =  new File("mkm_stock_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		temp.delete();
	}
	
	
	public boolean changeQte(Article a, int qte) throws IOException, MkmException, MkmNetworkException
	{
		ArrayList<Article> list = new ArrayList<Article>();
		list.add(a);
		return changeQte(list, qte);
	}
	
	public boolean changeQte(List<Article> list, int qte) throws IOException, MkmException, MkmNetworkException
	{
		String link ="https://www.mkmapi.eu/ws/v2.0/stock";
		
		if(qte>0)
			link+="/increase";
		else
			link+="/decrease";
		
		logger.debug("LINK="+link);
	    
		HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
		connection.addRequestProperty("Authorization", auth.generateOAuthSignature2(link,"PUT")) ;
		connection.setDoOutput(true);
		connection.setRequestMethod("PUT");
		connection.connect();
		MkmAPIConfig.getInstance().updateCount(connection);
		
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

		StringBuffer temp = new StringBuffer();

		temp.append("<?xml version='1.0' encoding='UTF-8' ?>");
		temp.append("<request>");

		for(Article a : list)
		{
			temp.append("<article>");
				temp.append("<idProduct>").append(a.getIdProduct()).append("</idProduct>");
				temp.append("<amount>").append(Math.abs(qte)).append("</amount>");
			temp.append("</article>");
			
			a.setCount(a.getCount()+qte);
			
		}		    
		temp.append("</request>");
		out.write(temp.toString());
		out.close();
		boolean ret= (connection.getResponseCode()>=200 && connection.getResponseCode()<300);
	 	 if(!ret)
	 		throw new MkmNetworkException(connection.getResponseCode());
		
		return ret;
	}
	
}

