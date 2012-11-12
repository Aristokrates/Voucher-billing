package org.pan.voucher.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="digital_product")
public class DigitalProduct extends BaseEntity {

	private static final long serialVersionUID = -5100191856486497541L;

	@Column(name="pin")
	private String pin;
	
	@Column(name="serial")
	private String serial;
	
	@Column(name="digital_product_price", scale=2)
	private BigDecimal costPrice;
	
	@OneToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "product_id")
	private Product product;

	public DigitalProduct(String pin, String serial, BigDecimal costPrice) {
		super();
		this.pin = pin;
		this.serial = serial;
		this.costPrice = costPrice;
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

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
