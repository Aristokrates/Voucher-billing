package org.pan.voucher.remoting.model;

public class Voucher {
	
	private String pin = "";
	
	private String serial = "";
	
	private float costprice;	

	public Voucher() {
		super();
	}

	public Voucher(String pin, String serial, float costprice) {
		super();
		this.pin = pin;
		this.serial = serial;
		this.costprice = costprice;
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
