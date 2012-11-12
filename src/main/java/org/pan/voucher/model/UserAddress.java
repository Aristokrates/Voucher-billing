package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class UserAddress extends BaseEntity {

	private static final long serialVersionUID = -7257532861298025536L;
	
	@Column(name = "address1")
	private String address1;
	
	@Column(name = "address2")
	private String address2;
	
	@Column(name = "address3")
	private String address3;
	
	@Column(name = "address4")
	private String adddress4;
	
	@Column(name = "address_town")
	private String town;
	
	@Column(name = "address_country")
	private String country;
	
	@Column(name = "address_postcode")
	private String postcode;
	
	@Column(name = "address_datecreate")
	private Date dateCreated;
	
	@Column(name = "address_datemod")
	private Date lastModifiedDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="address_user_id")
	private User user;

	public UserAddress() {
		super();
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAdddress4() {
		return adddress4;
	}

	public void setAdddress4(String adddress4) {
		this.adddress4 = adddress4;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

	public void updateStateFromUserModelAddress(UserAddress modelAddress) {
		
		this.address1 = modelAddress.getAddress1();
		this.address2 = modelAddress.getAddress2();
		this.address3 = modelAddress.getAddress3();
		this.adddress4 = modelAddress.getAdddress4();
		
		this.country = modelAddress.getCountry();
		this.postcode = modelAddress.getPostcode();
		this.town = modelAddress.getTown();
		this.lastModifiedDate = new Date();		
	}
}
