package org.pan.voucher.exception;

public class UserNotEligibleToBuyException extends RuntimeException {

	private static final long serialVersionUID = -6799570266232645327L;

	public UserNotEligibleToBuyException() {
		super();
	}

	public UserNotEligibleToBuyException(String username, String productName) {
		super(String.format("User %s not eligible to buy product %s", username, productName));
	}

}
