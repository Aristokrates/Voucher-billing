package org.pan.voucher.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
@NamedQueries({
	@NamedQuery(name="getTransactionsByUser", query="select t from Transaction t where t.user=:user order by t.dateCreated desc"),
	@NamedQuery(name="getBoughtVoucherTransactionsByUser", query="select t from Transaction t join fetch t.boughtVouchers b where t.user=:user and t.transationType='Purchase' order by t.dateCreated desc"),
	@NamedQuery(name="getTopupTransactionsByUser", query="select t from Transaction t where t.user=:user and t.transationType='Credit_Top_up' order by t.dateCreated desc")
})
public class Transaction extends BaseEntity {

	private static final long serialVersionUID = 3673639866848344042L;

	@Enumerated(EnumType.STRING)
	private TransactionType transationType;

	@Column(name = "transaction_mobilenr")
	private String mobileNumber;
	
	@Column(name = "transaction_ammount", scale=2)
	private BigDecimal amount;
	
	@Column(name = "transaction_debit", scale=2)
	private BigDecimal debit;
	
	@Column(name = "transaction_credit", scale=2)
	private BigDecimal credit;
	
	@Column(name = "transaction_balance", scale=2)
	private BigDecimal balance;
	
	@Column(name = "transaction_datecreate")
	private Date dateCreated;
	
	@Column(name = "transaction_datemod")
	private Date lastModifiedDate;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "transaction_user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "transaction_product_id")
	private Product product;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, targetEntity=BoughtVoucher.class, mappedBy="transaction")
	private List<BoughtVoucher> boughtVouchers = new ArrayList<BoughtVoucher>();

	public Transaction() {
		super();
	}

	public TransactionType getTransationType() {
		return transationType;
	}

	public void setTransationType(TransactionType transationType) {
		this.transationType = transationType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<BoughtVoucher> getBoughtVouchers() {
		return boughtVouchers;
	}

	public void setBoughtVouchers(List<BoughtVoucher> boughtVouchers) {
		this.boughtVouchers = boughtVouchers;
	}
	
	public void addBoughtVoucher(BoughtVoucher boughtVoucher) {
		getBoughtVouchers().add(boughtVoucher);
	}
}
