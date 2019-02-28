package org.api.mkm.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.api.mkm.modele.InsightElement;
import org.api.mkm.tools.MkmConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InsightService {

	private String url = MkmConstants.MKM_SITE_URL+"/en/Magic/Data";
	private Logger logger = LogManager.getLogger(this.getClass());

	private List<InsightElement> parse(Elements trs) {

		List<InsightElement> list = new ArrayList<>();
		for(Element tr : trs)
		{
			Elements tds = tr.select("td");
			InsightElement table = new InsightElement();
			
			String edition = tds.get(1).select("span").attr("title");
			String productURL = MkmConstants.MKM_SITE_URL+tds.get(2).select("a").attr("href");
			String productName = tds.get(2).select("a").text();
			double yesterdayPrice = parsePrice(tds.get(3).text());
			double price = parsePrice(tds.get(5).text());
			
			table.setCardName(productName);
			table.setEd(edition);
			table.setUrl(productURL);
			table.setPrice(price);
			table.setYesterdayPrice(yesterdayPrice);
			table.setChangeValue( (price-yesterdayPrice)/yesterdayPrice*100);

			list.add(table);
		}
		return list;
	}

	
	private Elements getTableTrsFor(String url) throws IOException
	{
		logger.debug("reading " + url);
		Document doc = Jsoup.connect(url).userAgent(MkmConstants.USER_AGENT).get();
		return doc.select("table>tbody>tr");
	}
	
	private double parsePrice(String strprice)
	{
		   strprice = strprice.substring(0, strprice.indexOf(' ')).replace(',', '.');
		   return Double.parseDouble(strprice);
	}
	
	public List<InsightElement> getTopCards(int interval) throws IOException
	{
		Elements trs = getTableTrsFor(url+"/Top-Cards?interval="+interval);
		List<InsightElement> list = new ArrayList<>();
		for(Element tr : trs)
		{
			Elements tds = tr.select("td");
			InsightElement table = new InsightElement();
			
			String edition = tds.get(1).select("span").attr("title");
			String productURL = MkmConstants.MKM_SITE_URL+tds.get(2).select("a").attr("href");
			String productName = tds.get(2).select("a").text();
			double prices = parsePrice(tds.get(3).text());
			
			
			table.setCardName(productName);
			table.setEd(edition);
			table.setPrice(prices);
			table.setUrl(productURL);
			
			list.add(table);
		}
		return list;
	}
	
	public List<InsightElement> getBestBargain() throws IOException
	{
		Elements trs = getTableTrsFor(url+"/Best-Bargains");
		List<InsightElement> list = new ArrayList<>();
		for(Element tr : trs)
		{
			Elements tds = tr.select("td");
			InsightElement table = new InsightElement();
			
			String edition = tds.get(1).select("span").attr("title");
			String productURL = MkmConstants.MKM_SITE_URL+tds.get(2).select("a").attr("href");
			String productName = tds.get(2).select("a").text();
			double prices = parsePrice(tds.get(3).text());
			
			
			table.setCardName(productName);
			table.setEd(edition);
			table.setPrice(prices);
			table.setUrl(productURL);
			
			list.add(table);
		}
		return list;
	}
	
	
	
	public List<InsightElement> getHighestPercentStockReduction() throws IOException
	{
		Elements trs = getTableTrsFor(url+"/Stock-Reduction");
		List<InsightElement> list = new ArrayList<>();
		for(Element tr : trs)
		{
			Elements tds = tr.select("td");
			InsightElement table = new InsightElement();
			
			String edition = tds.get(1).select("span").attr("title");
			String productURL = MkmConstants.MKM_SITE_URL+tds.get(2).select("a").attr("href");
			String productName = tds.get(2).select("a").text();
			int yesterdayStock = Integer.parseInt(tds.get(3).text());
			int stock = Integer.parseInt(tds.get(5).text());
			
			table.setCardName(productName);
			table.setEd(edition);
			table.setUrl(productURL);
			table.setYesterdayStock(yesterdayStock);
			table.setStock(stock);
			table.setChangeValue((((double)stock-yesterdayStock)/yesterdayStock)*100);
			list.add(table);
		}
		return list;
		
	}
	
	public List<InsightElement> getStartingPriceIncrease(boolean foil) throws IOException
	{
		String link="/Starting-Price-Increse";
		if(foil)
			link="/Starting-Price-Increse-Foils";
		
		Elements trs = getTableTrsFor(url+link);
		
		return parse(trs);
		
	}
	
	public List<InsightElement> getBiggestAvgSalesPriceIncrease(boolean foil) throws IOException
	{
		
		String link="/Average-Sale-Price-Increase";
		if(foil)
			link="/Average-Sale-Price-Increase-Foils";
		
		
		Elements trs = getTableTrsFor(url+link);
		return parse(trs);
		
	}
	
	
}
