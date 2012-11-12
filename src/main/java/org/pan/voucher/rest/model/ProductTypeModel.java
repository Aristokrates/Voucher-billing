package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductTypeModel {
	
	@JsonProperty
	private Integer id;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String description;

	public ProductTypeModel() {
		super();
	}

	public ProductTypeModel(Integer id, String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
