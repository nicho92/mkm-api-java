# mkm-api-java
Java api for MagicCardMarket v2.0

Download link : 
	Version 0.5 : https://github.com/nicho92/mkm-api-java/blob/master/dist
	
Usage : 

```java
		MkmAPIConfig.getInstance().init(ACCESS_TOKEN_SECRET,ACCESS_TOKEN,APP_SECRET,APP_TOKEN);
			CartServices cartService = new CartServices(); //manage your basket
			ArticleService artServices = new ArticleService(); //search articles
			WantsService wanServices = new WantsService(); //manage your wantlist
			ProductServices prodServices = new ProductServices();//find products
			StockService stockServices = new StockService();//manage your stock
			MKMService mkmServices = new MKMService();//get mkm games, get expansions
```		
		
		
