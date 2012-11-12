package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "prodtype", uniqueConstraints = {
		@UniqueConstraint(columnNames="prodType_name")
})
@NamedQueries({
	@NamedQuery(name="findProductTypeByName", query="select p from ProductType p where p.name = :name")
})
public class ProductType extends BaseEntity {

	private static final long serialVersionUID = -4186691241755412648L;

	@Column(name = "prodType_name")
	private String name;
	
	@Column(name = "prodType_desc")
	private String description;
	
	@Column(name = "prodType_datecreate")
	private Date dateCreated;
	
	@Column(name = "prodType_datemod")
	private Date dateModified;

	public ProductType() {
		super();
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
