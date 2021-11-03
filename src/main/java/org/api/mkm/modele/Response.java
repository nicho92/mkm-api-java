package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Product> product;
	private List<Article> article;
	private List<LightArticle> lightArticles;
	private List<Link> links;
	private List<Wantslist> wantslist;
	private List<Game> game;
	private List<Expansion> expansion;
	
	private List<Product> single;
	private List<Order> order;
	private List<Thread> thread;
	private List<User> users;

	private Error errors;
	private User account;
	private String productsfile;
	private String mime;
	private String priceguidefile;
	private String stock;
	private List<Inserted> inserted;
	private List<LightArticle> updatedArticles;
	private List<LightArticle> notUpdatedArticles;
	
	
	
	public List<LightArticle> getNotUpdatedArticles() {
		return notUpdatedArticles;
	}
	public void setNotUpdatedArticles(List<LightArticle> notUpdatedArticles) {
		this.notUpdatedArticles = notUpdatedArticles;
	}
	public List<LightArticle> getUpdatedArticles() {
		return updatedArticles;
	}
	public void setUpdatedArticles(List<LightArticle> updatedArticles) {
		this.updatedArticles = updatedArticles;
	}
	public List<LightArticle> getLightArticles() {
		return lightArticles;
	}
	public void setLightArticles(List<LightArticle> lightArticles) {
		this.lightArticles = lightArticles;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Thread> getThread() {
		return thread;
	}
	public void setThread(List<Thread> thread) {
		this.thread = thread;
	}
	public Error getErrors() {
		return errors;
	}
	public void setErrors(Error errors) {
		this.errors = errors;
	}
	public List<Order> getOrder() {
		return order;
	}
	public void setOrder(List<Order> order) {
		this.order = order;
	}
	public List<Product> getSingle() {
		return single;
	}
	public void setSingle(List<Product> single) {
		this.single = single;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public List<Game> getGame() {
		return game;
	}
	public void setGame(List<Game> game) {
		this.game = game;
	}
	public List<Expansion> getExpansion() {
		return expansion;
	}
	public void setExpansion(List<Expansion> expansion) {
		this.expansion = expansion;
	}
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
	public List<Inserted> getInserted() {
		return inserted;
	}

	public void setInserted(List<Inserted> inserted) {
		this.inserted = inserted;
	}
	
	
}
