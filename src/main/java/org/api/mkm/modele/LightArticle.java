package org.api.mkm.modele;

import java.io.Serializable;

public class LightArticle implements Serializable{

	private int idArticle;
	private int idProduct;
	private Localization language;
	private String comments;
	private double price;
	private int count;
	private boolean inShoppingCart;
	private double priceEUR;
	private double priceGBP;
	private LightProduct product;
	private String condition;
	private boolean isFoil;
	private boolean isSigned;
	private boolean isPlayset;
	private boolean isAltered;
	
	@Override
	public String toString() {
		if(getProduct()!=null)
			return getProduct().toString();
		
		
		return super.toString();
	}
	
	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public Localization getLanguage() {
		return language;
	}
	public void setLanguage(Localization language) {
		this.language = language;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isInShoppingCart() {
		return inShoppingCart;
	}
	public void setInShoppingCart(boolean inShoppingCart) {
		this.inShoppingCart = inShoppingCart;
	}
	public double getPriceEUR() {
		return priceEUR;
	}
	public void setPriceEUR(double priceEUR) {
		this.priceEUR = priceEUR;
	}
	public double getPriceGBP() {
		return priceGBP;
	}
	public void setPriceGBP(double priceGBP) {
		this.priceGBP = priceGBP;
	}
	public LightProduct getProduct() {
		return product;
	}
	public void setProduct(LightProduct product) {
		this.product = product;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public boolean isFoil() {
		return isFoil;
	}
	public void setFoil(boolean isFoil) {
		this.isFoil = isFoil;
	}
	public boolean isSigned() {
		return isSigned;
	}
	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}
	public boolean isPlayset() {
		return isPlayset;
	}
	public void setPlayset(boolean isPlayset) {
		this.isPlayset = isPlayset;
	}
	public boolean isAltered() {
		return isAltered;
	}
	public void setAltered(boolean isAltered) {
		this.isAltered = isAltered;
	}
	
	
	
}
