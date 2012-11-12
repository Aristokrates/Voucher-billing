package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(value = "register")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRegisterModel {
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String mobile;
	
	@JsonProperty
	private String profileEmail;
	
	@JsonProperty
	private String country;	
	
	@JsonProperty
	private String town;
	
	@JsonProperty
	private String postcode;
	
	@JsonProperty
	private String userType;
	
	public UserRegisterModel() {
		super();
	}
	
	public UserRegisterModel(UserRegisterModel model) {
		super();
		this.username = model.getUsername();
		this.password = model.getPassword();
		this.userType = model.getUserType();
		this.country = model.getCountry();
		this.mobile = model.getMobile();
		this.postcode = model.getPostcode();
		this.profileEmail = model.getProfileEmail();
		this.town = model.getTown();
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

	public String getProfileEmail() {
		return profileEmail;
	}

	public void setProfileEmail(String profileEmail) {
		this.profileEmail = profileEmail;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
}
