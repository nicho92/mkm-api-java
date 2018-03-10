package org.api.mkm.modele;

import java.io.Serializable;

public class Error implements Serializable {

	private String errorMessage;
	private String details;
	
	public String getError() {
		return errorMessage;
	}
	public void setError(String error) {
		this.errorMessage = error;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String detail) {
		this.details = detail;
	}
	
	@Override
	public String toString() {
		return getError() +":"+getDetails();
	}
	
	
}
