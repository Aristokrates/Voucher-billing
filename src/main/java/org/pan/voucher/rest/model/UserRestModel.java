package org.pan.voucher.rest.model;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(value = "user")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRestModel extends UserRegisterModel {
	
	@JsonProperty
	private Integer userId;
	
	@JsonProperty
	private String firstName;
	
	@JsonProperty
	private String middleName;
	
	@JsonProperty
	private String lastName;
	
	@JsonProperty
	private String profileDesc;
	
	@JsonProperty
	private byte[] profilePhoto;
	
	@JsonProperty
	private String profileMobile2;
	
	@JsonProperty
	private String profileFixedTel;
	
	@JsonProperty
	private String profileTwitter;
	
	@JsonProperty
	private String profileFacebook;

	@JsonProperty
	private String address1;
	
	@JsonProperty
	private String address2;
	
	@JsonProperty
	private String address3;
	
	@JsonProperty
	private String address4;
	
	@JsonProperty
	private BigDecimal userBalance;
	
	@JsonProperty
	private Boolean active;

	public UserRestModel() {
		super();
	}
	
	public UserRestModel(UserRegisterModel registerModel) {
		super(registerModel);
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileDesc() {
		return profileDesc;
	}

	public void setProfileDesc(String profileDesc) {
		this.profileDesc = profileDesc;
	}

	public String getProfileTwitter() {
		return profileTwitter;
	}

	public void setProfileTwitter(String profileTwitter) {
		this.profileTwitter = profileTwitter;
	}

	public String getProfileFacebook() {
		return profileFacebook;
	}

	public void setProfileFacebook(String profileFacebook) {
		this.profileFacebook = profileFacebook;
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

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getProfileMobile2() {
		return profileMobile2;
	}

	public void setProfileMobile2(String profileMobile2) {
		this.profileMobile2 = profileMobile2;
	}

	public String getProfileFixedTel() {
		return profileFixedTel;
	}

	public void setProfileFixedTel(String profileFixedTel) {
		this.profileFixedTel = profileFixedTel;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public BigDecimal getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(BigDecimal userBalance) {
		this.userBalance = userBalance;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
