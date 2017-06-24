package org.api.mkm.exceptions;

public class MkmException extends AbstractMKMException {

	public MkmException(org.api.mkm.modele.Error error) {
		super(error.toString());
	}
	
	public MkmException(String string) {
		super(string);
	}
}
