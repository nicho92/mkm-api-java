package org.api.mkm.modele;

public class Error {

	String error;
	String details;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
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
