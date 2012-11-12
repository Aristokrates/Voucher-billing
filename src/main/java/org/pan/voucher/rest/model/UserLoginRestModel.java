package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(value = "login")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserLoginRestModel {
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String password;

	public UserLoginRestModel() {
		super();
	}
	
	public UserLoginRestModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
}
