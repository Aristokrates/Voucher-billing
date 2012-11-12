package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.UserTypeDao;
import org.pan.voucher.model.UserType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("userTypeDao")
public class UserTypeDaoImpl extends BaseDaoImpl<UserType, Integer> implements UserTypeDao {

	@Override
	public UserType findUserTypeByName(final String name) {
		return getHibernateTemplate().execute(new HibernateCallback<UserType>() {

			@Override
			public UserType doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("findUserTypeByName");
				query.setString("name", name);
				return (UserType) query.uniqueResult();
			}
		});
	}

}
