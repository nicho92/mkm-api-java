package org.api.mkm.modele;

import java.io.Serializable;

public class LightProduct implements Serializable {
	private int idGame;
	private String enName;
	private String locName;
	private String image;
	private String expansion;
	private String nr;
	private Integer expIcon;
	private String rarity;
	private int idProduct;
	
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	
	@Override
	public String toString() {
		return getEnName();
	}
	
	public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getExpansion() {
		return expansion;
	}
	public void setExpansion(String expansion) {
		this.expansion = expansion;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public Integer getExpIcon() {
		return expIcon;
	}
	public void setExpIcon(Integer expIcon) {
		this.expIcon = expIcon;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	
}
