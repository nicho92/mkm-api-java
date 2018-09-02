package org.api.mkm.modele;

import java.io.Serializable;
import java.util.List;

public class Thread implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User partner;
	private Message message;
	private int unreadMessages;
	private List<Link> links;
	
	@Override
	public String toString() {
		return partner + ":" + message; 
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public int getUnreadMessages() {
		return unreadMessages;
	}
	public void setUnreadMessages(int unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
	public User getPartner() {
		return partner;
	}
	public void setPartner(User partner) {
		this.partner = partner;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	
	
	

}
