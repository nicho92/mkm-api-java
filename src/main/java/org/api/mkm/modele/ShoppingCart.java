package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

public class ShoppingCart implements Serializable{

	private int idReservation;
	private boolean isBuyer;
	private User seller;
	private List<Article> article;
	
	private double articleValue;
	private double articleCount;
	
	private ShippingMethod shippingMethod;
	
	private double totalValue;

	public Integer getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(Integer idReservation) {
		this.idReservation = idReservation;
	}

	public boolean isBuyer() {
		return isBuyer;
	}

	public void setBuyer(boolean isBuyer) {
		this.isBuyer = isBuyer;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public List<Article> getArticle() {
		return article;
	}

	public void setArticle(List<Article> article) {
		this.article = article;
	}

	public double getArticleValue() {
		return articleValue;
	}

	public void setArticleValue(double articleValue) {
		this.articleValue = articleValue;
	}

	public double getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(double articleCount) {
		this.articleCount = articleCount;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	
	
	
	
}
