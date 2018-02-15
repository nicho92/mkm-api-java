package org.api.mkm.exceptions;

import java.io.IOException;

public class MkmException extends IOException {

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
