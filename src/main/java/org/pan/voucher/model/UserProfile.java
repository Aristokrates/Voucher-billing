package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class UserProfile extends BaseEntity {

	private static final long serialVersionUID = 3275150651260068365L;

	@Column(name = "profile_desc")
	private String description;
	
	@Column(name = "profile_photo")
	@Lob
	private byte[] photo;
	
	@Column(name = "profile_mobile2")
	private String mobile2;
	
	@Column(name = "profile_fixedtel")
	private String fixedTel;
	
	@Column(name = "profile_email")
	private String email;
	
	@Column(name = "profile_twitter")
	private String twitter;
	
	@Column(name = "profile_facebook")
	private String facebook;
	
	@Column(name = "profile_datecreate")
	private Date dateCreated;
	
	@Column(name = "profile_datemod")
	private Date lastModifiedDate;
	
	@OneToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "profile_user_id")
	private User user;

	public UserProfile() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getFixedTel() {
		return fixedTel;
	}

	public void setFixedTel(String fixedTel) {
		this.fixedTel = fixedTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
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

	public void updteStateFromUserModelProfile(UserProfile modelProfile) {
		
		this.description = modelProfile.getDescription();
		this.email = modelProfile.getEmail();
		this.facebook = modelProfile.getFacebook();
		this.twitter = modelProfile.getTwitter();
		this.fixedTel = modelProfile.getFixedTel();
		this.mobile2 = modelProfile.getMobile2();
		this.photo = modelProfile.getPhoto();
		this.lastModifiedDate = new Date();		
	}
}
