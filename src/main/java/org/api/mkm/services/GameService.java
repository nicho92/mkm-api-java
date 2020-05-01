package org.api.mkm.services;

import java.io.IOException;
import java.util.List;

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
	
	
	
	

}
