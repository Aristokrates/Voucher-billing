package org.pan.voucher.exception;

public class UserPermissionException extends RuntimeException {

	private static final long serialVersionUID = 6280443148371770287L;

	public UserPermissionException() {
		super();
	}

	public UserPermissionException(String username) {
		super("Permission denied for user: " + username);
	}
}
