package org.pan.voucher.remoting.model;

public class VoucherListRequest {
	
	private int user;
	
	private String pass = "";
	
	private String refno = "";
	
	private String network = "";
	
	private int sellvalue;
	
	private int count;

	public VoucherListRequest() {
		super();
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public int getSellvalue() {
		return sellvalue;
	}

	public void setSellvalue(int sellvalue) {
		this.sellvalue = sellvalue;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
