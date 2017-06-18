package org.api.mkm.modele;

import java.util.List;

public class Response {
	
	List<Product> product;
	List<Article> article;
	List<Link> links;
	List<Wantslist> wantslist;
	User account;
	String productsfile;
	String mime;
	String priceguidefile;

	public User getAccount() {
		return account;
	}
	public void setAccount(User account) {
		this.account = account;
	}
	public List<Wantslist> getWantslist() {
		return wantslist;
	}
	public void setWantslist(List<Wantslist> wantlist) {
		this.wantslist = wantlist;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<Article> getArticle() {
		return article;
	}
	public void setArticle(List<Article> article) {
		this.article = article;
	}
	public String getProductsfile() {
		return productsfile;
	}
	public void setProductsfile(String productsfile) {
		this.productsfile = productsfile;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public String getPriceguidefile() {
		return priceguidefile;
	}
	public void setPriceguidefile(String priceguidefile) {
		this.priceguidefile = priceguidefile;
	}

	

}
