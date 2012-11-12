package org.pan.voucher.rest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.pan.voucher.rest.json.CustomJacksonDeserializer;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TransactionRestModel {
	
	@JsonProperty
	private String transactionType;
	
	@JsonProperty
	private String mobileNumber;
	
	@JsonProperty
	private BigDecimal ammount;
	
	@JsonProperty
	private BigDecimal debit;
	
	@JsonProperty
	private BigDecimal credit;
	
	@JsonProperty
	private BigDecimal balance;
	
	@JsonProperty
	@JsonDeserialize(using = CustomJacksonDeserializer.class)
	private Date dateCreated;
	
	@JsonProperty()
	@JsonDeserialize(using = CustomJacksonDeserializer.class)
	private Date dateModified;
	
	@JsonProperty
	private List<VoucherRestModel> vouchers = new ArrayList<VoucherRestModel>();
	
	@JsonProperty
	private Integer userId;

	public TransactionRestModel() {
		super();
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<VoucherRestModel> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<VoucherRestModel> vouchers) {
		this.vouchers = vouchers;
	}
}
