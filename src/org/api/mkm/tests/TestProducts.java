package org.api.mkm.tests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.ProductServices;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;
import org.magic.api.pricers.impl.MagicCardMarketPricer;

public class TestProducts {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		MagicCardMarketPricer pricer = new MagicCardMarketPricer();
		MkmAPIConfig.getInstance().init(pricer.getProperty("APP_ACCESS_TOKEN_SECRET").toString(),
										pricer.getProperty("APP_ACCESS_TOKEN").toString(),
										pricer.getProperty("APP_SECRET").toString(),
										pricer.getProperty("APP_TOKEN").toString());
		
		
		ArticleService artServices = new ArticleService();
		WantsService wanServices = new WantsService();
		ProductServices prodServices = new ProductServices();
		
		Wantslist ls = wanServices.createWantList("TEST");
		
		
		Map<PRODUCT_ATTS, String> map = new HashMap<PRODUCT_ATTS,String>();
		map.put(PRODUCT_ATTS.idGame, "1");
		map.put(PRODUCT_ATTS.idLanguage, "1");
		map.put(PRODUCT_ATTS.exact, "1");
		
		List<Product> prods = prodServices.find("Tarmogoyf", map);
		
		List<WantItem> lst = new ArrayList<WantItem>();
		
		for(Product p : prods)
		{
			
			WantItem it = new WantItem();
					 it.setProduct(p);
					 it.setType("product");
					 it.getIdLanguage().add(1);
					 it.setAltered(false);
					 it.setProduct(p);
					 it.setWishPrice(10.0);
					 it.setMailAlert(false);
					 it.setMinCondition("NM");
					 it.setCount(1);
			lst.add(it);
		}
		
		
		wanServices.addItem(ls, lst);
		
		
		
		
		/*
		
		Map<ARTICLES_ATT, String> map = new HashMap<ARTICLES_ATT,String>();
		
		map.put(ARTICLES_ATT.idLanguage, "1");
		map.put(ARTICLES_ATT.start, "0");
		map.put(ARTICLES_ATT.maxResults, "5");
		
		
		List<Article> pcs = artServices.find("295893",map);
		for(Article a : pcs)
		{
			System.out.println(a.getPrice());
			System.out.println(a.getSeller());
		}
		
		*/
		
		/*
		
		ArticleService artServices = new ArticleService();
		Map<ARTICLES_ATT, String> map = new HashMap<ARTICLES_ATT,String>();
		
		
		Product p = prodServices.getById("15145");
		
		map.put(ARTICLES_ATT.start, "0");
		map.put(ARTICLES_ATT.maxResults,"20");
		map.put(ARTICLES_ATT.idLanguage, "1");
		map.put(ARTICLES_ATT.minCondition, "NM");
		
		System.out.println(p.getPriceGuide().getAVG());
		
		List<Article> arts = artServices.find(p, map);
		
		for(Article art : arts)
		{
			art.setProduct(p);
			System.out.println(BeanUtils.describe(art));
		}
		*/

	}

}
