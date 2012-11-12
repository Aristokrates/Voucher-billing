package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName(value="vendor")
@JsonIgnoreProperties(ignoreUnknown=true)
public class VendorRestModel {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private Integer vendorId;

	public VendorRestModel() {
		super();
	}

	public VendorRestModel(String name, Integer vendorId) {
		super();
		this.name = name;
		this.vendorId = vendorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
}
