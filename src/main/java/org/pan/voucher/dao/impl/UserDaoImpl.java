package org.pan.voucher.dao.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.UserDao;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserTypeName;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

	@Override
	@Deprecated
	public User findUserByUsernameAndPassword(final String username,
			final String password) {
		return getHibernateTemplate().execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("findUserByUsernameAndPassword");
				query.setString("username", username);
				query.setString("password", password);
				return (User) query.uniqueResult();
			}
		});
	}
	
	@Override
	public User findUserByMobileAndPassword(final String mobile, final String password) {
		return getHibernateTemplate().execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("findUserByMobileAndPassword");
				query.setString("mobile", mobile);
				query.setString("password", password);
				return (User) query.uniqueResult();
			}
		});
	}

	@Override
	public Long countByUsername(final String username) {
        return (Long) getHibernateTemplate().execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                final Query query = session.getNamedQuery("countUsersByUsername");
                query.setParameter("username", username);
                return query.uniqueResult();
            }
        });
	}
	
	@Override
	public Long countByMobile(final String mobile) {
        return (Long) getHibernateTemplate().execute(new HibernateCallback<Object>() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                final Query query = session.getNamedQuery("countUsersByMobile");
                query.setParameter("mobile", mobile);
                return query.uniqueResult();
            }
        });
	}

	@Override
	public List<User> getUsersByType(final UserTypeName... type) {
		return getHibernateTemplate().execute(new HibernateCallback<List<User>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<User> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getUsersByType");
				query.setParameterList("typeList", Arrays.asList(type));
				return query.list();
			}
		});
	}

	@Override
	public User getUserById(final Integer userId) {
		return getHibernateTemplate().execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getUserByUserId");
				query.setInteger("userId", userId);
				return (User) query.uniqueResult();
			}
		});
	}

	@Override
	public User findUserByUsername(final String username) {
		
		return getHibernateTemplate().execute(new HibernateCallback<User>() {

			@Override
			public User doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("findUserByUsername");
				query.setString("username", username);
				return (User) query.uniqueResult();
			}
		});
	}
}
