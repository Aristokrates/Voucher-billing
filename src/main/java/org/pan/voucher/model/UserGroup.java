package org.pan.voucher.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user_group", uniqueConstraints = {
		@UniqueConstraint(columnNames="name")
})
@NamedQueries({
	@NamedQuery(name="getUserGroupById", query="select g from UserGroup g join fetch g.ownerUser o join fetch g.groupMembers m join fetch g.nonRegisteredGroupMembers n where g.id=:userGroupId"),
	@NamedQuery(name="getUserGroupByName", query="select g from UserGroup g where g.name=:userGroupName")
})
public class UserGroup extends BaseEntity {

	private static final long serialVersionUID = 2341519565324302229L;

	@Column(name="name")
	private String name;

	@Column(name="created_date")
	private Date dateCreated;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="owner_user_id")
	private User ownerUser;

	@ManyToMany(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinTable(
			name="user_group_members",
			joinColumns=@JoinColumn(name="user_group_id", nullable = false),
			inverseJoinColumns=@JoinColumn(name="user_id", nullable = false)
	)
	private Set<User> groupMembers = new HashSet<User>();
	
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=NonRegisteredUser.class)
	@JoinTable(
			name="user_group_nonregistered_members",
			joinColumns=@JoinColumn(name="user_group_id", nullable = false),
			inverseJoinColumns=@JoinColumn(name="non_registered_user_id", nullable = false)
	)
	private Set<NonRegisteredUser> nonRegisteredGroupMembers = new HashSet<NonRegisteredUser>();

	public UserGroup() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
	
	public Set<User> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(Set<User> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public void addGroupMember(User member) {
		getGroupMembers().add(member);
	}

	public Set<NonRegisteredUser> getNonRegisteredGroupMembers() {
		return nonRegisteredGroupMembers;
	}

	public void setNonRegisteredGroupMembers(
			Set<NonRegisteredUser> nonRegisteredGroupMembers) {
		this.nonRegisteredGroupMembers = nonRegisteredGroupMembers;
	}

	public void addNonRegisteredMember(NonRegisteredUser nonRegisteredMember) {
		getNonRegisteredGroupMembers().add(nonRegisteredMember);
	}
}
