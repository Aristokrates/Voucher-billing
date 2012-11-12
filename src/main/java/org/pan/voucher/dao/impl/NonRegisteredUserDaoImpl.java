package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.NonRegisteredUserDao;
import org.pan.voucher.model.NonRegisteredUser;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("nonRegisteredUserDao")
public class NonRegisteredUserDaoImpl extends
		BaseDaoImpl<NonRegisteredUser, Integer> implements NonRegisteredUserDao {

	@Override
	public NonRegisteredUser getNonRegisteredUserByNumber(final Long number, final Integer countryCode) {
		return getHibernateTemplate().execute(new HibernateCallback<NonRegisteredUser>() {

			@Override
			public NonRegisteredUser doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery("getNonRegisteredUserByNumber");
				query.setLong("number", number);
				query.setInteger("countryCode", countryCode);
				return (NonRegisteredUser) query.uniqueResult();
			}
		});
	}


}
