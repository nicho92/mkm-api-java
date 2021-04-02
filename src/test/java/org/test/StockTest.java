package org.test;

import java.io.File;
import java.io.IOException;

import org.api.mkm.modele.Article;
import org.api.mkm.modele.Localization;
import org.api.mkm.services.StockService;
import org.api.mkm.tools.MkmAPIConfig;
import org.junit.Test;

public class StockTest {

	
	public void update() throws IOException
	{
		MkmAPIConfig.getInstance().init(new File("C:\\Users\\Nicolas\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf"));
		Article a = new Article();
				a.setIdProduct(316216);
				a.setIdArticle(1029011500);
				a.setLanguage(new Localization(1, "English"));
				a.setComments("Test from mkm api");
				a.setCount(1);
				a.setCondition("NM");
				a.setPrice(12000.00);
				a.setFoil(false);
				a.setAltered(false);
				a.setSigned(false);
		
		StockService serv = new StockService();
		
		serv.updateArticles(a);
		
	}
	
}
