package org.api.mkm.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.api.mkm.modele.Article;
import org.api.mkm.modele.Article.ARTICLES_ATT;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.CartServices;
import org.api.mkm.services.MKMService;
import org.api.mkm.services.ProductServices;
import org.api.mkm.services.StockService;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;
import org.magic.api.pricers.impl.MagicCardMarketPricer2;

public class TestProducts {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		MagicCardMarketPricer2 pricer = new MagicCardMarketPricer2();
		
		MkmAPIConfig.getInstance().init(pricer.getProperty("APP_ACCESS_TOKEN_SECRET").toString(),
										pricer.getProperty("APP_ACCESS_TOKEN").toString(),
										pricer.getProperty("APP_SECRET").toString(),
										pricer.getProperty("APP_TOKEN").toString());
		
		
		CartServices cartService = new CartServices();
		
		
		ArticleService artServices = new ArticleService();
		WantsService wanServices = new WantsService();
		ProductServices prodServices = new ProductServices();
		
		StockService stockServices = new StockService();
		
		
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Map<PRODUCT_ATTS,String> atts = new HashMap<Product.PRODUCT_ATTS, String>();
								 atts.put(PRODUCT_ATTS.exact,"true");
		List<Product> search = prodServices.find("Snapcaster Mage", atts);
		Product p = search.get(1);
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		Map<ARTICLES_ATT,String> attp = new HashMap<ARTICLES_ATT, String>();
								 attp.put(ARTICLES_ATT.idLanguage, "1");
								 attp.put(ARTICLES_ATT.start, "0");
								 attp.put(ARTICLES_ATT.maxResults, "10");
								 
		List<Article> articles = artServices.find(p, attp);
		
		Article a = articles.get(0);
			a.setCondition("NM");
			a.setCount(10);
			a.setFoil(false);
			a.setPrice(10000);
			a.setPlayset(false);
			a.setComments("WS TEST");
		
			stockServices.addArticle(a);
			stockServices.removeArticle(a);
		
		/*cartService.addArticles(articles);
		cartService.empty();
		*/
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		List<Wantslist> wantLists = wanServices.getWantList();
		Wantslist wl = wantLists.get(0);
		wanServices.loadItems(wl);
		for(WantItem it: wl.getItem())
			System.out.println(it +" " + it.getProduct().getExpansionName());
		*/
		
		
		
	}

}
