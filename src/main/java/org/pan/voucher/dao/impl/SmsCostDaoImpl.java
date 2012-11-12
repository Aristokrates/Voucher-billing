package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.SmsCostDao;
import org.pan.voucher.model.SmsCost;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("smsCostDao")
public class SmsCostDaoImpl extends BaseDaoImpl<SmsCost, Integer> implements SmsCostDao {

	@Override
	public SmsCost getSmsCostByCountryCode(final Integer countryCode) {
		return getHibernateTemplate().execute(new HibernateCallback<SmsCost>() {

			@Override
			public SmsCost doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.getNamedQuery("getSmsCostByCountryCode");
				query.setInteger("countryCode", countryCode);				
				return (SmsCost) query.uniqueResult();
			}
			
		});
	}

}
