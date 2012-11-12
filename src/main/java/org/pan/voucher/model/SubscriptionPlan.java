package org.pan.voucher.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "subscription_plan",uniqueConstraints = {
		@UniqueConstraint(columnNames="policy")
})
@NamedQueries({
	@NamedQuery(name = "countByPolicy", query="select count(*) from SubscriptionPlan s where s.policy=:policy")
})
public class SubscriptionPlan extends BaseEntity {
	
	private static final long serialVersionUID = -7524388507762585124L;

	@Enumerated(EnumType.STRING)
	@Column(name = "policy")
	private SubscriptionPolicy policy;

	@Column(name = "price", scale=2)
	private BigDecimal price;

	public SubscriptionPlan() {
		super();
	}

	public SubscriptionPlan(SubscriptionPolicy policy, BigDecimal price) {
		super();
		this.policy = policy;
		this.price = price;
	}

	public SubscriptionPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(SubscriptionPolicy policy) {
		this.policy = policy;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
