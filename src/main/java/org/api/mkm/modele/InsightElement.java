package org.api.mkm.modele;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

public class InsightElement implements Serializable {

	
	private String ed;
	private String cardName;
	private double price;
	private double yesterdayPrice;
	private double changeValue;
	private String url;
	private int stock;
	private int yesterdayStock;
	
	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			return getCardName();
		} 
	}
	
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getYesterdayStock() {
		return yesterdayStock;
	}
	public void setYesterdayStock(int yesterdayStock) {
		this.yesterdayStock = yesterdayStock;
	}
	public double getYesterdayPrice() {
		return yesterdayPrice;
	}
	public void setYesterdayPrice(double yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}
	
	public String getEd() {
		return ed;
	}
	public void setEd(String ed) {
		this.ed = ed;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public double getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(double changeValue) {
		this.changeValue = changeValue;
	}
	
	
}
