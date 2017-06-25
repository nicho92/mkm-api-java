package org.api.mkm.exceptions;

public class MkmException extends Exception {

	public MkmException(org.api.mkm.modele.Error error) {
		super(error.toString());
	}
	
	public MkmException(String string) {
		super(string);
	}
}
