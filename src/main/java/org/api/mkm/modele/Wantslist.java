package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

public class Wantslist implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idWantsList;
	private Game game;
	private String name;
	private int itemCount;
	private List<WantItem> item;
	private List<Link> links;
	
	@Override
	public String toString() {
		return getName();
	}
	
	
	public int getIdWantsList() {
		return idWantsList;
	}


	public void setIdWantsList(int idWantsList) {
		this.idWantsList = idWantsList;
	}


	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public List<WantItem> getItem() {
		return item;
	}
	public void setItem(List<WantItem> item) {
		this.item = item;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	
	
}
