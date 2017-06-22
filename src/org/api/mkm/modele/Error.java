package org.api.mkm.modele;

public class Error {

	String error;
	String detail;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@Override
	public String toString() {
		return getError() +":"+getDetail();
	}
	
	
}
