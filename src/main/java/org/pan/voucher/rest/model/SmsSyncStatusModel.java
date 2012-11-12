package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class SmsSyncStatusModel {
	
	@JsonProperty
	private SmsSyncPayload payload;

	public SmsSyncStatusModel() {
		super();
	}
	
	public SmsSyncStatusModel(SmsSyncPayload payload) {
		super();
		this.payload = payload;
	}

	public SmsSyncPayload getPayload() {
		return payload;
	}

	public void setPayload(SmsSyncPayload payload) {
		this.payload = payload;
	}
}
