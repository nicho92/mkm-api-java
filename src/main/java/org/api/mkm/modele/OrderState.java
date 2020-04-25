package org.api.mkm.modele;

import java.io.Serializable;
import java.util.Date;

public class OrderState implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
	private Date dateBought;
	private Date datePaid;
	private Date dateSent;
	private Date dateReceived;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getDateBought() {
		return dateBought;
	}
	public void setDateBought(Date dateBought) {
		this.dateBought = dateBought;
	}
	public Date getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public Date getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	
	
}
