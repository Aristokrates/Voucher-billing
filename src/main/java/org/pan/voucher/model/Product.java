package org.pan.voucher.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
@NamedQueries({
	@NamedQuery(name = "getProductById", query="select p from Product p join fetch p.vendor v join fetch p.productType t where p.isDeleted = false and p.id=:productId"),
	@NamedQuery(name="getProductsByVendor", query="select p from Product p left join fetch p.productType t where p.isDeleted = false and p.vendor=:vendor")
})
public class Product extends BaseEntity {
	
	private static final long serialVersionUID = -8868671022133806877L;

	@Column(name = "product_name")
	private String name;
	
	@Column(name = "product_desc")
	private String description;
	
	@Column(name = "product__transfee", scale=2)
	private BigDecimal transfee;
	
	@Column(name = "product_costPrice", scale=2)
	private BigDecimal costPrice;
	
	@Column(name = "product_salesPrice", scale=2)
	private BigDecimal salesPrice;
	
	@Column(name = "product_commision", scale=2)
	private BigDecimal commision;
	
	@Column(name = "product_datecreate")
	private Date dateCreated;
	
	@Column(name = "product_datemod")
	private Date lastModifiedDate;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_prodType_id")
	private ProductType productType;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "product_vendor_id", nullable = false)
	private Vendor vendor;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="product")
	private DigitalProduct digitalProduct;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity=Transaction.class, mappedBy = "product")
	private Set<Transaction> transactions = new HashSet<Transaction>();	

	public Product() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getTransfee() {
		return transfee;
	}

	public void setTransfee(BigDecimal transfee) {
		this.transfee = transfee;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
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

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public DigitalProduct getDigitalProduct() {
		return digitalProduct;
	}

	public void setDigitalProduct(DigitalProduct digitalProduct) {
		this.digitalProduct = digitalProduct;
	}

	public void updateStateFromProductModel(Product modelProduct) {
		
		this.name = modelProduct.getName();
		this.description = modelProduct.getDescription();
		this.transfee = modelProduct.getTransfee();
		this.costPrice = modelProduct.getCostPrice();
		this.salesPrice = modelProduct.getSalesPrice();
		this.commision = modelProduct.getCommision();
		this.productType = modelProduct.getProductType();
		this.vendor = modelProduct.getVendor();
		this.lastModifiedDate = new Date();
		
	}
}
