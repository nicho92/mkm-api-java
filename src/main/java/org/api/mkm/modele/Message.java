package org.api.mkm.modele;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

	private int idMessage;
	private boolean isSending;
	private Date date;
	private String text;
	private boolean unread;
	
	
	@Override
	public String toString() {
		return getText();
	}
	
	
	public int getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}
	public boolean isSending() {
		return isSending;
	}
	public void setSending(boolean isSending) {
		this.isSending = isSending;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isUnread() {
		return unread;
	}
	public void setUnread(boolean unread) {
		this.unread = unread;
	}
	
	
	
}
