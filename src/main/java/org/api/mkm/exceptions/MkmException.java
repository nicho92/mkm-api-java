package org.api.mkm.exceptions;

import java.io.IOException;

public class MkmException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MkmException(org.api.mkm.modele.Error error) {
		super(error.toString());
	}
	
	public MkmException(String string) {
		super(string);
	}

	public MkmException(Exception e) {
		super(e);
	}
}
