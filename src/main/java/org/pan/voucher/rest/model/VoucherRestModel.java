package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(value = "voucher")
@JsonIgnoreProperties(ignoreUnknown=true)
public class VoucherRestModel {
	
	@JsonProperty
	private boolean errorOccured;
	
	@JsonProperty
	private String errorMessage;
	
	@JsonProperty
	private String pin;
	
	@JsonProperty
	private String serial;
	
	@JsonProperty
	private String vendor;

	public VoucherRestModel() {
		super();
	}
	
	public VoucherRestModel(boolean errorOccured, String errorMessage,
			String pin, String serial, String vendor) {
		super();
		this.errorOccured = errorOccured;
		this.errorMessage = errorMessage;
		this.pin = pin;
		this.serial = serial;
		this.vendor = vendor;
	}



	public boolean isErrorOccured() {
		return errorOccured;
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
}
