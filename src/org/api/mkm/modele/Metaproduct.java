package org.api.mkm.modele;

import java.util.List;

public class Metaproduct {

	private int idMetaproduct;
	private List<Localization> localization;
	private List<Product> product;
	private List<Link> link;
	
	private List<Localization> name;
	
	@Deprecated
	public List<Localization> getName() {
		return name;
	}
	@Deprecated
	public void setName(List<Localization> name) {
		this.name = name;
	}
	public int getIdMetaproduct() {
		return idMetaproduct;
	}
	public void setIdMetaproduct(int idMetaproduct) {
		this.idMetaproduct = idMetaproduct;
	}
	public List<Localization> getLocalization() {
		return localization;
	}
	public void setLocalization(List<Localization> localization) {
		this.localization = localization;
	}
	public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public List<Link> getLink() {
		return link;
	}
	public void setLink(List<Link> link) {
		this.link = link;
	}
	
	
}
