package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notice")
public class VendorNotice extends BaseEntity {

	private static final long serialVersionUID = 2211788960916196806L;

	@Column(name = "notice_name")
	private String name;
	
	@Column(name = "notice_desc")
	private String description;
	
	@Column(name = "notice_datecreate")
	private Date dateCreated;
	
	@Column(name = "notice_datemod")
	private Date lastModifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "notice_vendor_id", nullable = false)
	private Vendor vendor;

	public VendorNotice() {
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModified) {
		this.lastModifiedDate = lastModified;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public void updateStateFromVendorNoticeModel(VendorNotice vendorNoticeModel) {
		this.name = vendorNoticeModel.getName();
		this.description = vendorNoticeModel.getDescription();
		this.lastModifiedDate = new Date();
	}
}
