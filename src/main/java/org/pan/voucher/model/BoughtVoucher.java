package org.pan.voucher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bought_voucher")
public class BoughtVoucher extends BaseEntity {
	
	private static final long serialVersionUID = 2152389419169766758L;

	@Column(name = "pin")
	private String pin;
	
	@Column(name = "serial")
	private String serial;
	
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name = "transaction_id")
	private Transaction transaction;
	
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
	

	public BoughtVoucher() {
		super();
	}

	public BoughtVoucher(String pin, String serial, Transaction transaction, Vendor vendor) {
		super();
		this.pin = pin;
		this.serial = serial;
		this.transaction = transaction;
		this.vendor = vendor;
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

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
}
