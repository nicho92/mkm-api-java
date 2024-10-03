package org.test;

import java.io.IOException;

import org.api.mkm.services.InsightService;
import org.junit.Test;

public class StockTest {

	
	
	@Test
	public void update() throws IOException
	{
		var serv = new InsightService();
		
		
		serv.getBestBargain().forEach(ie->{
			System.out.println(ie);
		});
		
	}
	
}
