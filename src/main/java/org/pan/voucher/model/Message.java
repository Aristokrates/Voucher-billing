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

@Entity
@Table(name="message")
@NamedQueries({
	@NamedQuery(name="getMessagesForUser", query="select distinct m from Message m left join m.sender s left join fetch m.nonRegisteredUsers left join fetch m.registeredUsers left join fetch m.userGroups where m.sender=:user order by m.sentDate desc")
})
public class Message extends BaseEntity {

	private static final long serialVersionUID = 5086239562380025220L;

	@Column(name = "text")
	private String text;
	
	@Column(name = "date_sent")
	private Date sentDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "sender_user_id")
	private User sender;
	
	@Column(name = "non_registered_sender")
	private String nonRegisteredSender;
	
	@ManyToMany(fetch = FetchType.LAZY, targetEntity=User.class)
	@JoinTable(
			name="non_registered_user_message",
			joinColumns=@JoinColumn(name="message_id", nullable = false),
			inverseJoinColumns=@JoinColumn(name="non_registered_user_id", nullable = false)
	)
	private Set<NonRegisteredUser> nonRegisteredUsers = new HashSet<NonRegisteredUser>();

	@ManyToMany(fetch = FetchType.LAZY, targetEntity=User.class)
	@JoinTable(
			name="registered_user_message",
			joinColumns=@JoinColumn(name="message_id", nullable = false),
			inverseJoinColumns=@JoinColumn(name="user_id", nullable = false)
	)
	private Set<User> registeredUsers = new HashSet<User>();

	@ManyToMany(fetch=FetchType.LAZY, targetEntity=UserGroup.class)
	@JoinTable(
			name="user_group_message",
			joinColumns=@JoinColumn(name="message_id", nullable = false),
			inverseJoinColumns=@JoinColumn(name="user_group_id", nullable = false)
	)
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();

	public Message() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Set<NonRegisteredUser> getNonRegisteredUsers() {
		return nonRegisteredUsers;
	}

	public void setNonRegisteredUsers(Set<NonRegisteredUser> nonRegisteredUsers) {
		this.nonRegisteredUsers = nonRegisteredUsers;
	}

	public void addNonRegisteredUser(NonRegisteredUser nonRegisteredUser) {
		getNonRegisteredUsers().add(nonRegisteredUser);
	}

	public Set<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(Set<User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}
	
	public void addRegisteredUser(User registeredUser) {
		getRegisteredUsers().add(registeredUser);
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}
	
	public void addUserGroup(UserGroup userGroup) {
		getUserGroups().add(userGroup);
	}

	public String getNonRegisteredSender() {
		return nonRegisteredSender;
	}

	public void setNonRegisteredSender(String nonRegisteredSender) {
		this.nonRegisteredSender = nonRegisteredSender;
	}
	
}
