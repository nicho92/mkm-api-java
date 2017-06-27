package org.api.mkm.modele;

import java.util.List;

public class Expansion {

	private int idExpansion;
	private String enName;
	private int expansionIcon;
	private String abbreviation;
	private String icon;
	private String releaseDate;
	private boolean isReleased;
	private int idGame;
	private List<Link> links;
	
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public boolean isReleased() {
		return isReleased;
	}

	public void setReleased(boolean isReleased) {
		this.isReleased = isReleased;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return getEnName();
	}
	
	public int getIdExpansion() {
		return idExpansion;
	}
	public void setIdExpansion(int idExpansion) {
		this.idExpansion = idExpansion;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public int getExpansionIcon() {
		return expansionIcon;
	}
	public void setExpansionIcon(int expansionIcon) {
		this.expansionIcon = expansionIcon;
	}
	
	
}
