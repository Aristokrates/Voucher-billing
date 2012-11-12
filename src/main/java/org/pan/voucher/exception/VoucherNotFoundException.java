package org.pan.voucher.exception;

public class VoucherNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -753897124251888571L;

	public VoucherNotFoundException() {
		super();
	}

	public VoucherNotFoundException(String message) {
		super(message);
	}
	
}
