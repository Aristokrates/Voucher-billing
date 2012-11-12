package org.pan.voucher.service;

import java.math.BigDecimal;
import java.util.List;

import org.pan.voucher.model.Subscription;
import org.pan.voucher.model.SubscriptionPlan;
import org.pan.voucher.model.SubscriptionPolicy;
import org.pan.voucher.model.User;

public interface SubscriptionService {
	
	void addSubscriptionPlan(SubscriptionPolicy subscriptionPolicy, BigDecimal price);
	
	void updateSubscriptionPlan(SubscriptionPlan subPlan, BigDecimal newPrice);
	
	List<SubscriptionPlan> findAllSubsriptionPlans();
	
	void addSubsription(SubscriptionPlan subscriptionPlan, User user);
	
	@Deprecated
	Subscription findSubscriptionByUserId(Integer userId);
	
}
