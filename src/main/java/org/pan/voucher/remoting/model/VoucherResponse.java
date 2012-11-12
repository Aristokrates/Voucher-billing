package org.pan.voucher.remoting.model;

public class VoucherResponse {
	
	private int status;
	
	private String message = "";
	
	private float balance;
	
	private String pin = "";
	
	private String serial = "";
	
	private float costprice;

	public VoucherResponse() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public float getCostprice() {
		return costprice;
	}

	public void setCostprice(float costprice) {
		this.costprice = costprice;
	}
}
