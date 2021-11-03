package org.api.mkm.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Inserted;
import org.api.mkm.modele.LightArticle;
import org.api.mkm.modele.LightProduct;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
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
	 		xstream.addImplicitCollection(Response.class, "inserted",Inserted.class);
	 		xstream.addImplicitCollection(Response.class, "updatedArticles", LightArticle.class);
	 		xstream.addImplicitCollection(Response.class, "notUpdatedArticles", LightArticle.class);
	}
	
	public List<LightArticle> readStockFile(File f , int idGame)
	{
			List<LightArticle> ret = new ArrayList<>();
			try(CSVParser p = CSVFormat.Builder.create().setDelimiter(";").setHeader().build().parse(new FileReader(f))  )
			{
				p.iterator().forEachRemaining(art->{
						var item = new LightArticle();
						var product = new LightProduct();
							  product.setIdGame(idGame);
							  product.setLocName(art.get("Local Name"));
							  product.setExpansion(art.get("Exp. Name"));
							  product.setEnName(art.get("English Name"));
							  product.setIdProduct(Integer.parseInt(art.get("idProduct")));
							  try {
								  item.setFoil(!art.get("Foil?").isEmpty());
								  item.setSigned(!art.get("Signed?").isEmpty());
								  item.setAltered(!art.get("Altered?").isEmpty());
							  }
							catch(IllegalArgumentException e)
							{
								//do nothing
							}
							  
							  item.setProduct(product);
							  item.setCount(Integer.parseInt(art.get("Amount")));
							  item.setPrice(Double.parseDouble(art.get("Price")));
							  item.setIdArticle(Integer.parseInt(art.get("idArticle")));
							  item.setComments(art.get("Comments"));
							  try {
								  var loc = Tools.listLanguages().get(Integer.parseInt(art.get("Language"))-1);
								  product.setLocName(loc.getLanguageName());
							  }
							  catch(Exception e)
							  {
								  logger.error("No language for code =" + art.get("Language"));
							  }
							  ret.add(item);
					});
			}
		catch(Exception e)
		{
			logger.error(e);
		}
			return ret;
	}
	
	public List<LightArticle> getStock() throws IOException
	{
		return getStock(1);
	}
	
	public List<LightArticle> getStock(int page) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/stock/"+page;
		
		String xml= Tools.getXMLResponse(link, "GET", getClass());

		//TODO ugly !!!! but need to reforge stockmanagement
		xml = xml.replace("<article>", "<lightArticles>").replace("</article>", "</lightArticles>");

		Response res = (Response)xstream.fromXML(xml);
		
		return res.getLightArticles();
	}
	
	public List<Article> getShoppingcartArticles() throws IOException
	{
		
		String link=MkmConstants.MKM_API_URL+"/stock/shoppingcart-articles";
		
		String xml= Tools.getXMLResponse(link, "GET", getClass());

		Response res = (Response)xstream.fromXML(xml);
		
		return res.getArticle();
	}
	
	
	public void exportStock(File f,Integer idGame,boolean sealed) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/stock/file";
	
		if(idGame!=null)
			link=MkmConstants.MKM_API_URL+"/stock/file?idGame="+idGame+"&isSealed="+sealed;
		else
			link=MkmConstants.MKM_API_URL+"/stock/file?idGame=1&isSealed=false";
				
		
		String xml= Tools.getXMLResponse(link, "GET", getClass());
		Response res = (Response)xstream.fromXML(xml);
	
		
		byte[] bytes = Base64.decodeBase64(res.getStock());
		File temp =  new File("mkm_temp.gz");
		FileUtils.writeByteArrayToFile(temp, bytes );
		Tools.unzip(temp, f);
	
		if(!Files.deleteIfExists(temp.toPath()))
		{
			logger.error("couln't remove " + temp.getAbsolutePath());
		}
	}
	
	public Inserted addArticle(Article a) throws IOException
	{
		return addArticles(List.of(a)).get(0);
	}
	
	public LightArticle updateArticles(Article a) throws IOException
	{
		return updateArticles(List.of(a)).get(0);
	}
	
	
	public List<LightArticle> updateArticles(List<Article> list) throws IOException
	{
		String link =MkmConstants.MKM_API_URL+"/stock";
		StringBuilder temp = new StringBuilder();
		temp.append(MkmConstants.XML_HEADER);
		temp.append("<request>");

		for(Article a : list)
		{
			temp.append("<article>");
				temp.append("<idProduct>").append(a.getIdProduct()).append("</idProduct>");
				temp.append("<idArticle>").append(a.getIdArticle()).append("</idArticle>");
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
		
		String xml = Tools.getXMLResponse(link, "PUT", getClass(), temp.toString());
		
		Response res = (Response)xstream.fromXML(xml);

		return res.getUpdatedArticles();
		
	}
	
	public List<Inserted> addArticles(List<Article> list) throws IOException
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
		
		String xml = Tools.getXMLResponse(link, "POST", getClass(), temp.toString());
		
		Response res = (Response)xstream.fromXML(xml);

		return res.getInserted();
		
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
