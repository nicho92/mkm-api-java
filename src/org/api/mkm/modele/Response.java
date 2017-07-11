package org.api.mkm.modele;

import java.util.List;

public class Response {
	
	List<Product> product;
	List<Article> article;
	List<Link> links;
	List<Wantslist> wantslist;
	List<Game> game;
	List<Expansion> expansion;
	List<Product> single;
	List<Order> order;
	List<Thread> thread;
	List<User> users;

	Error errors;
	User account;
	String productsfile;
	String mime;
	String priceguidefile;
	String stock;
	
	
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

}
