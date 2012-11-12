package org.pan.voucher.exception;

public class InvalidUserCredentials extends RuntimeException {

	private static final long serialVersionUID = 6286580271370433753L;
	
	public InvalidUserCredentials() {
		super();
	}

	public InvalidUserCredentials(String username) {
		super("Invalid credentials for user '" + username + "'");
	}

}
