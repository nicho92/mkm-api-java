package org.api.mkm.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.api.mkm.modele.Category;
import org.api.mkm.modele.Expansion;
import org.api.mkm.modele.Game;
import org.api.mkm.modele.Link;
import org.api.mkm.modele.Response;
import org.api.mkm.tools.MkmConstants;
import org.api.mkm.tools.Tools;

import com.thoughtworks.xstream.XStream;

public class GameService {

	private XStream xstream;
	private List<Game> games;
	
	public GameService() {
		xstream = Tools.instNewXstream();
	 		xstream.addImplicitCollection(Response.class,"game",Game.class);
	 		xstream.addImplicitCollection(Response.class, "expansion", Expansion.class);
	 		xstream.addImplicitCollection(Response.class, "links", Link.class);
	}
	
	
	public Game getGame(int id) throws IOException
	{
		if(games==null)
			games=listGames();
		
		for(Game g : games)
			if(g.getIdGame()==id)
				return g;
		
		return null;
		
	}
	
	
	public List<Game> listGames() throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/games";
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		games=res.getGame();
		
		return games;
		
	}
	
	
	public List<Expansion> listExpansion(Integer id) throws IOException
	{
		Game g = new Game();
		g.setIdGame(id);
		return listExpansion(g);
	}
	
	public List<Expansion> listExpansion(Game g) throws IOException
	{
		String link=MkmConstants.MKM_API_URL+"/games/"+g.getIdGame()+"/expansions";
		String xml= Tools.getXMLResponse(link, "GET", this.getClass());
		Response res = (Response)xstream.fromXML(xml);
		return res.getExpansion();
	}
	
	
	public List<Category> listCategories() throws IOException {
		var map = new HashMap<Integer,String>();
		map.put(14,"Albums");
		map.put(1081,"Apparel");
		map.put(1025,"Card Scanners");
		map.put(1020,"Cardfight!! Vanguard Booster");
		map.put(1027,"Cardfight!! Vanguard Booster Box");
		map.put(1033,"Cardfight!! Vanguard Legend Decks");
		map.put(1019,"Cardfight!! Vanguard Single");
		map.put(1032,"Cardfight!! Vanguard Start Decks");
		map.put(1031,"Cardfight!! Vanguard Trial Deck");
		map.put(1035,"CFV Set");
		map.put(1051,"DBS Set");
		map.put(15,"DeckBoxes");
		map.put(1048,"DGB Set");
		map.put(16,"Dice");
		map.put(49,"DiceBags");
		map.put(45,"Dividers");
		map.put(1042,"Dragoborne Booster");
		map.put(1045,"Dragoborne Booster Boxes");
		map.put(1047,"Dragoborne Demo Decks");
		map.put(1039,"Dragoborne Single");
		map.put(1046,"Dragoborne Trial Decks");
		map.put(1052,"Dragon Ball Super Booster Boxes");
		map.put(1050,"Dragon Ball Super Boosters");
		map.put(1055,"Dragon Ball Super Draft Boxes");
		map.put(1061,"Dragon Ball Super Expansion Sets");
		map.put(1049,"Dragon Ball Super Singles");
		map.put(1054,"Dragon Ball Super Special Packs");
		map.put(1053,"Dragon Ball Super Starter Decks");
		map.put(1030,"FF Set");
		map.put(1023,"Final Fantasy Booster");
		map.put(1028,"Final Fantasy Booster Box");
		map.put(1022,"Final Fantasy Single");
		map.put(1029,"Final Fantasy Starter Decks");
		map.put(1021,"Force of Will Booster");
		map.put(1026,"Force of Will Booster Box");
		map.put(1018,"Force of Will Single");
		map.put(1036,"Force of Will Starter Decks");
		map.put(1037,"Force of Will Vingolf Series");
		map.put(1038,"FoW Set");
		map.put(50,"GameKits");
		map.put(41,"GamingStones");
		map.put(43,"LifeCounter");
		map.put(2,"Magic Booster");
		map.put(7,"Magic Display");
		map.put(38,"Magic Event Tickets");
		map.put(17,"Magic Fatpack");
		map.put(18,"Magic Intropack");
		map.put(21,"Magic Lot");
		map.put(20,"Magic Miscellaneous");
		map.put(1056,"Magic Online Singles");
		map.put(1,"Magic Single");
		map.put(32,"Magic Starter Deck");
		map.put(19,"Magic Theme Deck Display");
		map.put(24,"Magic TournamentPack");
		map.put(46,"Memorabilia");
		map.put(1067,"MLP Set");
		map.put(8,"MtG Set");
		map.put(1044,"My Little Pony Booster");
		map.put(1062,"My Little Pony Booster Boxes");
		map.put(1065,"My Little Pony Collector Boxes");
		map.put(1068,"My Little Pony Figures");
		map.put(1041,"My Little Pony Singles");
		map.put(1063,"My Little Pony Theme Decks");
		map.put(1066,"My Little Pony Tins");
		map.put(35,"Playmats");
		map.put(13,"Pocket Pages");
		map.put(52,"PokÃ©mon Booster");
		map.put(1015,"PokÃ©mon Box Set");
		map.put(1017,"PokÃ©mon Coins");
		map.put(53,"PokÃ©mon Display");
		map.put(1016,"PokÃ©mon Elite Trainer Boxes");
		map.put(1064,"PokÃ©mon Lots");
		map.put(51,"PokÃ©mon Single");
		map.put(54,"PokÃ©mon Theme Deck");
		map.put(1014,"PokÃ©mon Tins");
		map.put(1013,"PokÃ©mon Trainer Kits");
		map.put(44,"PrintedMedia");
		map.put(12,"Sleeves");
		map.put(1075,"Star Wars: Destiny Booster Boxes");
		map.put(1074,"Star Wars: Destiny Boosters");
		map.put(1073,"Star Wars: Destiny Dice");
		map.put(1078,"Star Wars: Destiny Dice Binder");
		map.put(1077,"Star Wars: Destiny Draft Sets");
		map.put(1072,"Star Wars: Destiny Singles");
		map.put(1076,"Star Wars: Destiny Starter Sets");
		map.put(40,"Storage");
		map.put(1079,"SWD Set");
		map.put(10,"TelperinquÃ¡r Booster");
		map.put(9,"TelperinquÃ¡r Single");
		map.put(11,"TelperinquÃ¡r Starter");
		map.put(23,"The Spoils Booster");
		map.put(48,"The Spoils Display");
		map.put(22,"The Spoils Single");
		map.put(1080,"WeiÃŸ Schwarz Supply Set");
		map.put(1043,"WeiÎ² Schwarz Booster");
		map.put(1060,"WeiÎ² Schwarz Booster Boxes");
		map.put(1070,"WeiÎ² Schwarz Meister Sets");
		map.put(1069,"WeiÎ² Schwarz Trial Decks");
		map.put(1040,"Weiss Schwarz Single");
		map.put(4,"WOW Booster");
		map.put(39,"WOW Collector Set");
		map.put(30,"WOW Display");
		map.put(29,"WOW Epic Collection");
		map.put(25,"WOW Raid Deck");
		map.put(33,"WoW Set");
		map.put(3,"WOW Single");
		map.put(27,"WOW Starter Deck");
		map.put(26,"WOW Treasure Pack");
		map.put(1071,"WS Set");
		map.put(6,"Yugioh Booster");
		map.put(34,"Yugioh Collector Tins");
		map.put(42,"Yugioh Display");
		map.put(1024,"Yugioh Event Tickets");
		map.put(47,"Yugioh Lot");
		map.put(37,"Yugioh Promo Products");
		map.put(5,"Yugioh Single");
		map.put(31,"Yugioh Special Edition");
		map.put(28,"Yugioh Starter Deck");
		map.put(36,"Yugioh Structure Deck");

		
		return map.entrySet().stream().map(entry->{
			Category c = new Category();
			c.setIdCategory(entry.getKey());
			c.setCategoryName(entry.getValue());
			return c;
		}).collect(Collectors.toList());
		
		
	}
	
	
	
	

}
