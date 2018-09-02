package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum PRODUCT_ATTS {exact,idGame,idLanguage,start,maxResults}
	
	private int idProduct;
	private String idMetaproduct;
	private int idGame;
	private Integer countReprints;
	private String enName;
	private List<Localization> localization;
	private String website;
	private String image;
	private String gameName;
	private String categoryName;
	private String number;
	private String rarity;
	private String expansionName;
	private Expansion expansion;
	private PriceGuide priceGuide;
	private List<Expansion> reprint;
	private List<Link> links;
	private Category category;
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public String getIdMetaproduct() {
		return idMetaproduct;
	}
	public void setIdMetaproduct(String idMetaproduct) {
		this.idMetaproduct = idMetaproduct;
	}
	public Integer getCountReprints() {
		return countReprints;
	}
	public void setCountReprints(Integer countReprints) {
		this.countReprints = countReprints;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public List<Localization> getLocalization() {
		return localization;
	}
	public void setLocalization(List<Localization> localization) {
		this.localization = localization;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public String getExpansionName() {
		return expansionName;
	}
	public void setExpansionName(String expansionName) {
		this.expansionName = expansionName;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public Expansion getExpansion() {
		return expansion;
	}
	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
	}
	public PriceGuide getPriceGuide() {
		return priceGuide;
	}
	public void setPriceGuide(PriceGuide priceGuide) {
		this.priceGuide = priceGuide;
	}
	public List<Expansion> getReprint() {
		return reprint;
	}
	public void setReprint(List<Expansion> reprint) {
		this.reprint = reprint;
	}
	
	public int getIdGame() {
		return idGame;
	}
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	@Override
	public String toString() {
		return getEnName();
	}
	
	
	
	
	
	

}
