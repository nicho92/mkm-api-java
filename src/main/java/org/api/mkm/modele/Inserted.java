package org.api.mkm.modele;

import java.io.Serializable;

public class Inserted implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean success;
	private LightArticle idArticle;
	private LightArticle tried;
	private String error;
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public LightArticle getTried() {
		return tried;
	}
	public void setTried(LightArticle tried) {
		this.tried = tried;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public LightArticle getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(LightArticle idArticle) {
		this.idArticle = idArticle;
	}
	
	
}
