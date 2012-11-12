package org.pan.voucher.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="sms_cost")
@NamedQueries({
	@NamedQuery(name = "getSmsCostByCountryCode", query="select s from SmsCost s where s.countryCode=:countryCode")
})
public class SmsCost extends BaseEntity {
	
	private static final long serialVersionUID = -9052983364102281633L;

	@Column(name = "country_code")
	private Integer countryCode;
	
	@Column(name = "sms_cost", scale=2)
	private BigDecimal smsCost;

	public SmsCost() {
		super();
	}

	public SmsCost(Integer countryCode, BigDecimal smsCost) {
		super();
		this.countryCode = countryCode;
		this.smsCost = smsCost;
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public BigDecimal getSmsCost() {
		return smsCost;
	}

	public void setSmsCost(BigDecimal smsCost) {
		this.smsCost = smsCost;
	}
	
}
