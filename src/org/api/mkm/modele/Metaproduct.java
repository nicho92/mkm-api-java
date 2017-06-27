package org.api.mkm.modele;

import java.util.ArrayList;
import java.util.List;

public class Metaproduct {

	private int idMetaproduct;
	private List<Localization> localization;
	private List<Product> product;
	@Deprecated private List<Integer> idProduct;
	private List<Link> link;
		
	public static String ENGLISH="1";
	public static String FRENCH="2";
	public static String GERMAN="3";
	public static String SPANISH="4";
	public static String ITALIAN="5";
	
	public static String[] getLangs()
	{
		return new String[]{"ENGLISH","FRENCH","GERMAN","SPANISH","ITALIAN"};
	}
	
	public Metaproduct() {
		idProduct=new ArrayList<Integer>();
		localization = new ArrayList<Localization>();
	}
	
	
	@Deprecated
	public List<Integer> getIdProduct() {
		return idProduct;
	}
	@Deprecated
	public void setProduct(List<Integer> products) {
		this.idProduct = products;
	}
	
	@Override
	public String toString() {
		
		if(name==null)
			return localization.get(0).getLanguageName();
		else
			return name.get(0).getLanguageName();
	}
	
	@Deprecated private List<Localization> name;
	
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
	
	public List<Link> getLink() {
		return link;
	}
	public void setLink(List<Link> link) {
		this.link = link;
	}
	
	
}
