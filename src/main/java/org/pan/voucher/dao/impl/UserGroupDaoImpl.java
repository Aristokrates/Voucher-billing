package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.UserGroupDao;
import org.pan.voucher.model.UserGroup;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("userGroupDao")
public class UserGroupDaoImpl extends BaseDaoImpl<UserGroup, Integer> implements UserGroupDao {

	@Override
	public UserGroup getUserGroupById(final Integer userGroupId) {

		return getHibernateTemplate().execute(new HibernateCallback<UserGroup>() {

			@Override
			public UserGroup doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getUserGroupById");
				query.setInteger("userGroupId", userGroupId);
				return (UserGroup) query.uniqueResult();
			}
		});
	}

	@Override
	public UserGroup getUserGroupByName(final String userGroupName) {

		return getHibernateTemplate().execute(new HibernateCallback<UserGroup>() {

			@Override
			public UserGroup doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getUserGroupByName");
				query.setString("userGroupName", userGroupName);
				return (UserGroup) query.uniqueResult();
			}
		});
	}

}
