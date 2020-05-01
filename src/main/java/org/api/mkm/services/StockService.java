package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Game;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.modele.LightArticle;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class StockService {

	private XStream xstream;
	private Logger logger = LogManager.getLogger(this.getClass());

	public StockService() {
			xstream = Tools.instNewXstream();
	 		xstream.addImplicitCollection(Response.class, "links", Link.class);
	 		xstream.addImplicitCollection(Response.class, "lightArticles",LightArticle.class);

	}
	
	public List<LightArticle> getStock(int idGame,String name) throws IOException
	{
		Game g = new Game();
		g.setIdGame(idGame);
		return getStock(g, name);
	}
	
	public List<LightArticle> getStock() throws IOException
	{
		return getStock(null, null);
	}
	
	public List<LightArticle> getStock(Game game,String name) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/stock";
		
		if(name!=null)
			link=link+"/"+URLEncoder.encode(name, "UTF-8");
		
		if(game!=null)
			link=link+"/"+game.getIdGame();
		
		String xml= Tools.getXMLResponse(link, "GET", getClass());

		//TODO ugly !!!! but need to reforge stockmanagement
		xml = xml.replace("<article>", "<lightArticles>").replace("</article>", "</lightArticles>");

		Response res = (Response)xstream.fromXML(xml);
		
		return res.getLightArticles();
	}
	
	public void exportStock(File f,Integer idGame) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/stock/file";
	
		if(idGame!=null)
			link=MkmConstants.MKM_API_URL+"/stock/file?idGame="+idGame;
		
		String xml= Tools.getXMLResponse(link, "GET", getClass());
		Response res = (Response)xstream.fromXML(xml);
	
		
		byte[] bytes = Base64.decodeBase64(res.getPriceguidefile());
		File temp =  new File("mkm_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		if(!temp.delete())
		{
			logger.error("couln't remove " + temp.getAbsolutePath());
		}
	}
	
	
	
	public void addArticle(Article a) throws IOException
	{
		addArticles(List.of(a));
	}
	
	public void addArticles(List<Article> list) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/stock";
		StringBuilder temp = new StringBuilder();

		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request>");

		for(Article a : list)
		{
			temp.append("<article>");
				temp.append("<idProduct>").append(a.getIdProduct()).append("</idProduct>");
				temp.append("<count>").append(a.getCount()).append("</count>");
				if(a.getLanguage()!=null)
					temp.append("<idLanguage>").append(a.getLanguage().getIdLanguage()).append("</idLanguage>");
				
				if(a.getComments()!=null)
					temp.append("<comments>").append(a.getComments()).append("</comments>");
				
				temp.append("<price>").append(a.getPrice()).append("</price>");
				temp.append("<condition>").append(a.getCondition()).append("</condition>");
				temp.append("<isFoil>").append(a.isFoil()).append("</isFoil>");
				temp.append("<isSigned>").append(a.isSigned()).append("</isSigned>");
				temp.append("<isPlayset>").append(a.isPlayset()).append("</isPlayset>");
			temp.append("</article>");
		}		    
		temp.append("</request>");
		
		Tools.getXMLResponse(link, "POST", getClass(), temp.toString());
		
	}
	
	public void removeArticle(Article a) throws IOException
	{
		removeArticles(List.of(a));
	}
	
	public void removeArticles(List<Article> list) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/stock";
		StringBuilder temp = new StringBuilder();
					  temp.append(MkmConstants.XML_HEADER);
					  temp.append("<request>");
                    for(Article a : list)
					{
						temp.append("<article>");
							temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
							temp.append("<count>").append(a.getCount()).append("</count>");
						temp.append("</article>");
					}		    
					temp.append("</request>");
		Tools.getXMLResponse(link, "DELETE", this.getClass(), temp.toString());
	}
	
	public void exportStockFile(File f) throws IOException
	{
		exportStockFile(f,null);
	}
	
	public void exportStockFile(File f,Map<ARTICLES_ATT,String> atts) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/stock/file";
		
		if(atts!=null)
    	{
    		link+="?";
    		List<String> paramStrings = new ArrayList<>();
 	        for(Entry<ARTICLES_ATT, String> parameter:atts.entrySet())
	             paramStrings.add(parameter.getKey() + "=" + parameter.getValue());
	        
 	        link+=Tools.join(paramStrings, "&");
    	}
	
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		
		byte[] bytes = Base64.decodeBase64(res.getStock());
		File temp =  new File("mkm_stock_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
		if(!temp.delete())
		{
			logger.error("couldn't delete "+ temp.getAbsolutePath());
		}
	}
	
	public List<Article> getStockInShoppingCarts() throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/stock/shoppingcart-articles";
		String xml = Tools.getXMLResponse(link, "GET", this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		return res.getArticle();
	}
	
	public void changeQte(LightArticle a, int qte) throws IOException
	{
		changeQte(List.of(a), qte);
	}
	
	public void changeQte(List<LightArticle> list, int qte) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/stock";
		
		if(qte>0)
			link+="/increase";
		else
			link+="/decrease";
		
		StringBuilder temp = new StringBuilder();

		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request>");

		for(LightArticle a : list)
		{
			temp.append("<article>");
				temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
				temp.append("<amount>").append(Math.abs(qte)).append("</amount>");
			temp.append("</article>");
			
			a.setCount(a.getCount()+qte);
			
		}		    
		temp.append("</request>");
		Tools.getXMLResponse(link, "PUT", this.getClass(), temp.toString());
	}
	
}
