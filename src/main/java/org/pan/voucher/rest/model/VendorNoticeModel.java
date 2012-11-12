package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(value = "vendorNotice")
@JsonIgnoreProperties(ignoreUnknown=true)
public class VendorNoticeModel {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String description;
	
	public VendorNoticeModel() {
		super();
	}

	public VendorNoticeModel(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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
}
