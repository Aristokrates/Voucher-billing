package org.pan.voucher.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Pance.Isajeski
 */
@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="findUserByUsernameAndPassword", query="select u from User u left join fetch u.userCredential c where u.isDeleted = false and c.username=:username and c.password=:password"),
	@NamedQuery(name="findUserByMobileAndPassword", query="select u from User u left join fetch u.userCredential c where u.isDeleted = false and c.mobile=:mobile and c.password=:password"),
	@NamedQuery(name="countUsersByUsername", query="select count(*) from User u left join u.userCredential c where u.isDeleted = false and c.username=:username"),
	@NamedQuery(name="countUsersByMobile", query="select count(*) from User u left join u.userCredential c where u.isDeleted = false and c.mobile=:mobile"),
	@NamedQuery(name="findUserByUsername", query="select u from User u left join u.userCredential c where u.isDeleted = false and c.username=:username"),
	@NamedQuery(name="getUsersByType", query="select u from User u where u.isDeleted = false and u.userType.name in (:typeList)"),
	@NamedQuery(name="getUserByUserId", query="select u from User u where u.isDeleted = false and u.id=:userId")
})
public class User extends BaseEntity {

	private static final long serialVersionUID = 9218790601838923358L;

	@Column(name = "user_firstname")
	private String firstName;
	
	@Column(name = "user_middlename")
	private String middleName;
	
	@Column(name = "user_lastname")
	private String lastName;
	
	@Column(name = "user_balance", scale=2)
	private BigDecimal balance;
	
	@Column(name = "user_dob")
	private Date dob; //TODO what is this???
	
	@Column(name = "user_datecreate")
	private Date dateCreated;
	
	@Column(name = "user_datemod")
	private Date lastModifiedDate;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = false;
	
	@Column(name = "is_active")
	private Boolean isActive = false;
	
	@ManyToOne
	@JoinColumn(name="user_userType")
	private UserType userType;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="user")
	private Subscription userSubscription;
	
	@OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="user")
	private UserAddress userAddress;
	
	@OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="user")
	private UserCredential userCredential;
	
	@OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="user")
	private UserProfile userProfile;
	
	@OneToMany(fetch = FetchType.LAZY, targetEntity = Transaction.class, mappedBy="user")
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=UserGroup.class, mappedBy="ownerUser")
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=InboxMessage.class, mappedBy="inboxUser")
	private Set<InboxMessage> inboxMessages = new HashSet<InboxMessage>();

	@OneToMany(fetch=FetchType.LAZY, targetEntity=Message.class, mappedBy="sender")
	private Set<Message> sentMessages = new HashSet<Message>();

	public User() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public UserCredential getUserCredential() {
		return userCredential;
	}

	public void setUserCredential(UserCredential userCredential) {
		this.userCredential = userCredential;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public Set<InboxMessage> getInboxMessages() {
		return inboxMessages;
	}

	public void setInboxMessages(Set<InboxMessage> inboxMessages) {
		this.inboxMessages = inboxMessages;
	}

	public Set<Message> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(Set<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Subscription getUserSubscription() {
		return userSubscription;
	}

	public void setUserSubscription(Subscription userSubscription) {
		this.userSubscription = userSubscription;
	}

	public void updateStateFromUserModel(User userModel) {
		
		this.firstName = userModel.getFirstName();
		this.middleName = userModel.getMiddleName();
		this.lastName = userModel.getLastName();
		this.lastModifiedDate = new Date();
		
		UserAddress address = userModel.getUserAddress();
		if (address != null) {
			this.userAddress.updateStateFromUserModelAddress(address);
		}
		
		UserProfile profile = userModel.getUserProfile();
		if (profile != null) {
			this.userProfile.updteStateFromUserModelProfile(profile);
		}
	}
	
}
