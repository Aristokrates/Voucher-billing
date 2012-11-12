package org.pan.voucher.remoting.model;

import java.util.ArrayList;
import java.util.List;

public class VoucherListResponse {
	
	private int status;
	
	private String errorcode = "";
	
	private String message = "";
	
	private float balance;
	
	private List<Voucher> vouchers = new ArrayList<Voucher>();

	public VoucherListResponse() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
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

	public List<Voucher> getVouchers() {
		return vouchers;
	}

	public void setVauchers(List<Voucher> vauchers) {
		this.vouchers = vauchers;
	}
	
}
