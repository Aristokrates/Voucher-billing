package org.pan.voucher.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usertype", uniqueConstraints = {
		@UniqueConstraint(columnNames="userType_name")
})
@NamedQueries({
	@NamedQuery(name="findUserTypeByName", query="select n from UserType n left join fetch n.childUserTypes t where n.name = :name")
})
public class UserType extends BaseEntity {
	
	private static final long serialVersionUID = -7975938569589511058L;

	@Enumerated(EnumType.STRING)
	@Column(name = "userType_name")
	private UserTypeName name;
	
	@Column(name = "userType_desc")
	private String description;
	
	@Column(name = "userType_datecreate")
	private Date dateCreated;
	
	@Column(name = "userType_datemod")
	private Date lastModifiedDate;
	
	@ManyToMany(targetEntity=UserType.class)
	@JoinTable(
	    name="user_type_childrens",
	    joinColumns=@JoinColumn(name="parent_user_type_id", nullable = false),
	    inverseJoinColumns=@JoinColumn(name="child_user_type_id", nullable = false)
	)
	private List<UserType> childUserTypes = new ArrayList<UserType>();

	public UserType() {
		super();
	}

	public UserTypeName getName() {
		return name;
	}

	public void setName(UserTypeName name) {
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

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<UserType> getChildUserTypes() {
		return childUserTypes;
	}

	public void setChildUserTypes(List<UserType> userTypes) {
		this.childUserTypes = userTypes;
	}
	
	public void addUserType(UserType userType) {
		this.childUserTypes.add(userType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserType other = (UserType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
