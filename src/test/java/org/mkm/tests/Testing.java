package org.mkm.tests;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.api.mkm.services.OrderService;
import org.api.mkm.services.OrderService.ACTOR;
import org.api.mkm.services.OrderService.STATE;
import org.api.mkm.tools.MkmAPIConfig;
import org.junit.Test;

public class Testing {

	@Test
	public void test() throws IOException{
		
		Properties p = new Properties();
		p.load(new FileInputStream(new File("C:\\Users\\Nicolas\\.magicDeskCompanion\\pricers\\MagicCardMarket.conf")));
		
		try {
		MkmAPIConfig.getInstance().init(p);
		OrderService serv = new OrderService();
		serv.listOrders(ACTOR.buyer, STATE.received, null).forEach(o->{
			o.getArticle().forEach(a->{
				System.out.println(a.getProduct().getExpansion());
				System.out.println(a.getProduct().getExpansionName());
			});
			
		});
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
