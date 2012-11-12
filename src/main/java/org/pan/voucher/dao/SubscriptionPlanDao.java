package org.pan.voucher.dao;

import org.pan.voucher.model.SubscriptionPlan;
import org.pan.voucher.model.SubscriptionPolicy;

public interface SubscriptionPlanDao extends BaseDao<SubscriptionPlan, Integer> {

	Long countBySubscriptionPolicy(SubscriptionPolicy subscriptionPolicy);

}
