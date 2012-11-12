package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "access", uniqueConstraints = {
		@UniqueConstraint(columnNames="access_name"),
		@UniqueConstraint(columnNames="profile_mobile")
})
public class UserCredential extends BaseEntity {

	private static final long serialVersionUID = -2002595915493395396L;

	@Column(name = "access_name")
	private String username;
	
	@Column(name = "access_password")
	private String password;	
	
	@Column(name = "profile_mobile")
	private String mobile;
	
	@Column(name = "access_datecreate")
	private Date dateCreated;
	
	@Column(name = "access_datemod")
	private Date lastModifiedDate;
	
	@OneToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "access_user_id")
	private User user;
	
	public UserCredential() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
}
