package org.api.mkm.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Address shippingAddress;
	private List<ShoppingCart> shoppingCart;
	private User account;
	
	
	public Basket() {
		shoppingCart=new ArrayList<>();
	}
	@Override
	public String toString() {
		return getAccount() +" " + shoppingCart.size() + " cart(s)";
	}
	
	public Address getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public List<ShoppingCart> getShoppingCart() {
		return shoppingCart;
	}
	public void setShoppingCart(List<ShoppingCart> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	public User getAccount() {
		return account;
	}
	public void setAccount(User account) {
		this.account = account;
	}

	
	
}
