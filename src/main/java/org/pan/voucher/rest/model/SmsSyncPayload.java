package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class SmsSyncPayload {
	
	@JsonProperty
	private String success;

	public SmsSyncPayload() {
		super();
	}
	
	public SmsSyncPayload(String success) {
		super();
		this.success = success;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
