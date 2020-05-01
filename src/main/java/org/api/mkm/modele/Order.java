package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int idOrder;
		private boolean isBuyer;
		private User seller;
		private OrderState state;
		private ShippingMethod shippingMethod;
		private boolean isPresale;
		private Address shippingAddress;
		private int articleCount;
		private String note;
		private Evaluation evaluation;
		private List<LightArticle> article;
		private double articleValue;
		private double serviceFeeValue;
		private double totalValue;
		
		
		@Override
		public String toString() {
			return String.valueOf(getIdOrder());
		}
		
		
		public int getIdOrder() {
			return idOrder;
		}
		public void setIdOrder(int idOrder) {
			this.idOrder = idOrder;
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
		public OrderState getState() {
			return state;
		}
		public void setState(OrderState state) {
			this.state = state;
		}
		public ShippingMethod getShippingMethod() {
			return shippingMethod;
		}
		public void setShippingMethod(ShippingMethod shippingMethod) {
			this.shippingMethod = shippingMethod;
		}
		public boolean isPresale() {
			return isPresale;
		}
		public void setPresale(boolean isPresale) {
			this.isPresale = isPresale;
		}
		public Address getShippingAddress() {
			return shippingAddress;
		}
		public void setShippingAddress(Address shippingAddress) {
			this.shippingAddress = shippingAddress;
		}
		public int getArticleCount() {
			return articleCount;
		}
		public void setArticleCount(int articleCount) {
			this.articleCount = articleCount;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		public Evaluation getEvaluation() {
			return evaluation;
		}
		public void setEvaluation(Evaluation evaluation) {
			this.evaluation = evaluation;
		}
		public List<LightArticle> getArticle() {
			return article;
		}
		public void setArticle(List<LightArticle> article) {
			this.article = article;
		}
		public double getArticleValue() {
			return articleValue;
		}
		public void setArticleValue(double articleValue) {
			this.articleValue = articleValue;
		}
		public double getServiceFeeValue() {
			return serviceFeeValue;
		}
		public void setServiceFeeValue(double serviceFeeValue) {
			this.serviceFeeValue = serviceFeeValue;
		}
		public double getTotalValue() {
			return totalValue;
		}
		public void setTotalValue(double totalValue) {
			this.totalValue = totalValue;
		}
		
		
		
		
}
