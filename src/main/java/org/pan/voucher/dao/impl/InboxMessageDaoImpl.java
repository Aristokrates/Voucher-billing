package org.pan.voucher.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.InboxMessageDao;
import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.User;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("inboxMessageDao")
public class InboxMessageDaoImpl extends BaseDaoImpl<InboxMessage, Integer> implements InboxMessageDao {

	@Override
	public List<InboxMessage> getInboxMessagesForUser(final User user) {
		return getHibernateTemplate().execute(new HibernateCallback<List<InboxMessage>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<InboxMessage> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getInboxMessagesForUser");
				query.setEntity("user", user);
				return query.list();
			}
		});
	}
	

	@Override
	public void markAsRead(final Integer messageId) {
		
		getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query query = session.getNamedQuery("markAsRead");
				query.setInteger("messageId", messageId);
				return query.executeUpdate();
			}
		});
		
	}

}
