package org.pan.voucher.exception;

public class InvalidSessionTokenException extends RuntimeException {
	
    private static final long serialVersionUID = 3939099711846185845L;

    public InvalidSessionTokenException() {
        super();
    }

    public InvalidSessionTokenException(String sessionToken) {
        super("Invalid session token '" + sessionToken + "'");
    }

}
