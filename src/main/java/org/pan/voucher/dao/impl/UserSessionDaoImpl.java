package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.UserSessionDao;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserSession;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("userSessionDao")
public class UserSessionDaoImpl extends BaseDaoImpl<UserSession, Integer> implements UserSessionDao {

	@Override
	public void deleteIfExists(final User user) {

		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.getNamedQuery("deleteUserSessionByUser");
				query.setParameter("user", user);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public UserSession findBySessionToken(final String sessionToken) {
		return getHibernateTemplate().execute(new HibernateCallback<UserSession>() {

			@Override
			public UserSession doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("findBySessionToken");
				query.setString("sessionToken", sessionToken);
				return (UserSession) query.uniqueResult();
			}
		});
	}

}
