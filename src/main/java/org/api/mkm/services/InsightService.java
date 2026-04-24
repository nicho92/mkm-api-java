package org.api.mkm.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.api.mkm.tools.Tools;

public class InsightService {

	private static String urlProductSingle = "https://downloads.s3.cardmarket.com/productCatalog/productList/products_singles_1.json";
	private String urlPriceSingle = "https://downloads.s3.cardmarket.com/productCatalog/productList/products_singles_1.json";
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		
		Tools.download(new File("test.json"), urlProductSingle);
		
		
		
	}
	
	

	
}
