package org.pan.voucher.rest.model;

public class SmsSyncInputModel {
	
	private String from;
	
	private String message;
	
	private String secret;
	
	public SmsSyncInputModel() {
		super();
	}

	public SmsSyncInputModel(String from, String message, String secret) {
		super();
		this.from = from;
		this.message = message;
		this.secret = secret;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
