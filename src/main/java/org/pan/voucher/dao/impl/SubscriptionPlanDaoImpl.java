package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.SubscriptionPlanDao;
import org.pan.voucher.model.SubscriptionPlan;
import org.pan.voucher.model.SubscriptionPolicy;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("subscriptionPlanDao")
public class SubscriptionPlanDaoImpl extends BaseDaoImpl<SubscriptionPlan, Integer> implements SubscriptionPlanDao {

	@Override
	public Long countBySubscriptionPolicy(final SubscriptionPolicy subscriptionPolicy) {
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {

			@Override
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.getNamedQuery("countByPolicy");
				query.setString("policy", subscriptionPolicy.toString());
				return (Long) query.uniqueResult();
			}
		});
	}
	
	

}
