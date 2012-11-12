package org.pan.voucher.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "non_registered_user", uniqueConstraints = {
		@UniqueConstraint(columnNames={"number", "country_code"})
})
@NamedQueries({
	@NamedQuery(name = "getNonRegisteredUserByNumber", query = "select n from NonRegisteredUser n where n.number=:number and n.countryCode=:countryCode")
})
public class NonRegisteredUser extends BaseEntity {

	private static final long serialVersionUID = 2208442167377887587L;
	
	@Column(name = "number")
	private Long number;
	
	@Column(name = "country_code")
	private Integer countryCode;
	
	@Column(name = "is_notified")
	private boolean isNotified = false;
	
	public NonRegisteredUser() {
		super();
	}

	public NonRegisteredUser(Long number, Integer countryCode,
			boolean isNotified) {
		super();
		this.number = number;
		this.countryCode = countryCode;
		this.isNotified = isNotified;
	}



	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
	
	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public boolean isNotified() {
		return isNotified;
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}
	
	public String getInternationalNumber() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("+");
		buffer.append(getCountryCode());
		buffer.append(getNumber());
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonRegisteredUser other = (NonRegisteredUser) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
}
