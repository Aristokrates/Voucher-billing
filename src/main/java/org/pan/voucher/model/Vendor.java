package org.pan.voucher.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "vendor", uniqueConstraints = {
		@UniqueConstraint(columnNames="vendor_name")
})
@NamedQueries({
	@NamedQuery(name="getVendorWithNotices", query="select v from Vendor v left join fetch v.vendorNotices n where v.isDeleted = false and v.id=:vendorId"),
	@NamedQuery(name="getActiveVendors", query="select v from Vendor v where v.isDeleted=false"),
	@NamedQuery(name="getVendorByVendorId", query="select v from Vendor v where v.isDeleted=false and v.id=:vendorId")
})
public class Vendor extends BaseEntity {
	
	private static final long serialVersionUID = -4281901647108553662L;

	@Column(name = "vendor_name")
	private String name;
	
	@Column(name = "vendor_datecreate")
	private Date dateCreated;
	
	@Column(name = "vendor_datemod")
	private Date lastModifiedDate;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = false;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = VendorNotice.class, mappedBy="vendor")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Set<VendorNotice> vendorNotices = new HashSet<VendorNotice>();
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Product.class, mappedBy="vendor")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Set<Product> products = new HashSet<Product>();

	public Vendor() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<VendorNotice> getVendorNotices() {
		return vendorNotices;
	}

	public void setVendorNotices(Set<VendorNotice> vendorNotices) {
		this.vendorNotices = vendorNotices;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
