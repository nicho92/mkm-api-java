package org.api.mkm.exceptions;

public class MkmException extends Exception {

	public MkmException(Error e) {
		super(e.toString());
	}
}
