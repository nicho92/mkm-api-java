package org.api.mkm.modele;

import java.io.Serializable;

public class Localization implements Serializable{

	private int idLanguage;
	private String name;
	private String languageName;
	

	@Override
	public String toString() {
		return getLanguageName();
	}
	
	public int getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	
	/**
	 * 1 - English
2 - French
3 - German
4 - Spanish
5 - Italian
6 - Simplified Chinese
7 - Japanese
8 - Portuguese
9 - Russian
10 - Korean
11 - Traditional Chinese
	 * */
	
}
