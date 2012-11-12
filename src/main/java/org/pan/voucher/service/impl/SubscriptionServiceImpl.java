package org.pan.voucher.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateMidnight;
import org.joda.time.Period;
import org.pan.voucher.dao.SubscriptionDao;
import org.pan.voucher.dao.SubscriptionPlanDao;
import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.Subscription;
import org.pan.voucher.model.SubscriptionPlan;
import org.pan.voucher.model.SubscriptionPolicy;
import org.pan.voucher.model.User;
import org.pan.voucher.service.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("subscriptionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {
	
	@Resource private SubscriptionDao subscriptionDao;
	@Resource private SubscriptionPlanDao subscriptionPlanDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSubscriptionPlan(SubscriptionPolicy subscriptionPolicy, BigDecimal price) {
		
		Long count = subscriptionPlanDao.countBySubscriptionPolicy(subscriptionPolicy);
		if (count > 0) {
			throw new InvalidDataException("Subscription policy already exist");
		}
		subscriptionPlanDao.save(new SubscriptionPlan(subscriptionPolicy, price));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateSubscriptionPlan(SubscriptionPlan subPlan, BigDecimal newPrice) {
		
		subPlan.setPrice(newPrice);
		subscriptionPlanDao.merge(subPlan);
	}

	@Override
	public List<SubscriptionPlan> findAllSubsriptionPlans() {
		return subscriptionPlanDao.findAll();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSubsription(SubscriptionPlan subscriptionPlan, User user) {

		Date fromDate = null;
		Date toDate = null;
		
		SubscriptionPolicy policy = subscriptionPlan.getPolicy();
		
		calculateSubscriptionDates(fromDate, toDate, policy);
		
		Subscription subscription = new Subscription();
		subscription.setSubscriptionPlan(subscriptionPlan);
		subscription.setUser(user);
		subscription.setValidFrom(fromDate);
		subscription.setValidTo(toDate);
		
		subscriptionDao.save(subscription);
	}
		
	@Override
	@Deprecated
	public Subscription findSubscriptionByUserId(Integer userId) {
		//TODO if needed
		return null;
	}

	private void calculateSubscriptionDates(Date fromDate, Date toDate, SubscriptionPolicy policy) {
		
		DateMidnight midnight = new DateMidnight();
		
		switch (policy) {
		
		case MONTHLY:
			fromDate = midnight.toDate();
			toDate = midnight.plus(Period.months(1)).toDate();
			break;
		case ANNUAL:
			fromDate = midnight.toDate();
			toDate = midnight.plus(Period.years(1)).toDate();
			break;

		default:
			throw new IllegalArgumentException("Subscription policy not recognized");
		}

	}
}
