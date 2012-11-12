package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_session")
@NamedQueries({
	@NamedQuery(name="deleteUserSessionByUser", query="delete from UserSession s where s.user=:user"),
	@NamedQuery(name="findBySessionToken", query="select s from UserSession s join fetch s.user u where s.sessionToken=:sessionToken")
})
public class UserSession extends BaseEntity {

	private static final long serialVersionUID = -9079808195827722470L;

	@Column(name = "session_token")
	private String sessionToken;
	
	@Column(name = "token_issue_date")
	private Date tokenIssueDate;
	
	@OneToOne(targetEntity = User.class)
	private User user;	

	public UserSession() {
		super();
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public Date getTokenIssueDate() {
		return tokenIssueDate;
	}

	public void setTokenIssueDate(Date tokenIssueDate) {
		this.tokenIssueDate = tokenIssueDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
