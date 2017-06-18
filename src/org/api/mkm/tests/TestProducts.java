package org.api.mkm.tests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.api.mkm.modele.Product;
import org.api.mkm.modele.Product.PRODUCT_ATTS;
import org.api.mkm.modele.WantItem;
import org.api.mkm.modele.Wantslist;
import org.api.mkm.services.ArticleService;
import org.api.mkm.services.AuthenticationServices;
import org.api.mkm.services.ProductServices;
import org.api.mkm.services.WantsService;
import org.api.mkm.tools.MkmAPIConfig;
import org.magic.api.exports.impl.MKMOnlineWantListExport.WantList;
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
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Map<PRODUCT_ATTS,String> atts = new HashMap<Product.PRODUCT_ATTS, String>();
		atts.put(PRODUCT_ATTS.exact,"true");
		List<Product> search = prodServices.find("Snapcaster Mage", atts);
		for(Product p : search)
		{
			System.out.println(p);
		}
		
		
		
		
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
