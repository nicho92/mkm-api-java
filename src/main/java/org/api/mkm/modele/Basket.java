package org.api.mkm.modele;

import java.util.ArrayList;
import java.util.List;

public class Basket {
	Address shippingAddress;
	List<ShoppingCart> shoppingCart;
	User account;
	
	
	public Basket() {
		shoppingCart=new ArrayList<ShoppingCart>();
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