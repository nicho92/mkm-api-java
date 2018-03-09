package org.api.mkm.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WantItem implements Serializable{

	
			public static String[] CONDITIONS = {"MT","NM","EX","GD","LP","PL","PO"}; 
	
	
			private String idWant;         
		    int count;   
		    double wishPrice;      
		    private MkmBoolean mailAlert;
		    private String type;
		    private Product product;
		    private List<Integer> idLanguage;
		    private String minCondition;
		    private MkmBoolean isFoil;
		    private MkmBoolean isSigned;
		    private MkmBoolean isPlayset;
		    private MkmBoolean isAltered;
		    private MkmBoolean isFirstEd;//only yugiho
		    
		    
		    @Override
		    public String toString() {
		    	return String.valueOf(product);
		    }
		    
		    public WantItem() {
				idLanguage= new ArrayList<>();
			}
		    
		    
			public String getIdWant() {
				return idWant;
			}
			public void setIdWant(String idWant) {
				this.idWant = idWant;
			}
			public int getCount() {
				return count;
			}
			public void setCount(int count) {
				this.count = count;
			}
			public double getWishPrice() {
				return wishPrice;
			}
			public void setWishPrice(double wishPrice) {
				this.wishPrice = wishPrice;
			}
			public MkmBoolean isMailAlert() {
				return mailAlert;
			}
			public void setMailAlert(MkmBoolean mailAlert) {
				this.mailAlert = mailAlert;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public Product getProduct() {
				return product;
			}
			public void setProduct(Product product) {
				this.product = product;
			}
			public List<Integer> getIdLanguage() {
				return idLanguage;
			}
			public void setIdLanguage(List<Integer> idLanguage) {
				this.idLanguage = idLanguage;
			}
			public String getMinCondition() {
				return minCondition;
			}
			public void setMinCondition(String minCondition) {
				this.minCondition = minCondition;
			}
			public MkmBoolean isFoil() {
				return isFoil;
			}
			public void setFoil(MkmBoolean isFoil) {
				this.isFoil = isFoil;
			}
			public MkmBoolean isSigned() {
				return isSigned;
			}
			public void setSigned(MkmBoolean isSigned) {
				this.isSigned = isSigned;
			}
			public MkmBoolean isPlayset() {
				return isPlayset;
			}
			public void setPlayset(MkmBoolean isPlayset) {
				this.isPlayset = isPlayset;
			}
			public MkmBoolean isAltered() {
				return isAltered;
			}
			public void setAltered(MkmBoolean isAltered) {
				this.isAltered = isAltered;
			}
			public MkmBoolean isFirstEd() {
				return isFirstEd;
			}
			public void setFirstEd(MkmBoolean isFirstEd) {
				this.isFirstEd = isFirstEd;
			}

	
}
