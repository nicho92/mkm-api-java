package org.api.mkm.modele;

import java.io.Serializable;

public class Game implements Serializable{
	 private int idGame;
	 private String name; 
	 private String abbreviation;
	 private Link links;

	 @Override
	 public String toString() {
		return getName();
	}
	 
	 
	 public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public Link getLinks() {
		return links;
	}
	public void setLinks(Link links) {
		this.links = links;
	}
	 
	 
	 
}
