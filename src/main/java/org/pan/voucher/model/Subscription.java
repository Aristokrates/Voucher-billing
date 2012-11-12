package org.pan.voucher.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.joda.time.DateMidnight;

@Entity
@Table(name="subscription")
public class Subscription extends BaseEntity {

	private static final long serialVersionUID = 1659562630850095071L;
	
	@Column(name = "valid_from", nullable=false)
	private Date validFrom;
	
	@Column(name = "valid_to", nullable=false)
	private Date validTo;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "subscription_plan_id")
	private SubscriptionPlan subscriptionPlan;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Subscription() {
		super();
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public SubscriptionPlan getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isValid() {
		
		DateMidnight fromDateTime = new DateMidnight(getValidFrom().getTime());
		DateMidnight toDateTime = new DateMidnight(getValidTo().getTime());

		if (fromDateTime.isBeforeNow() && toDateTime.isAfterNow()) {
			return true;
		}
		
		return false;
	}
}
