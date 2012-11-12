package org.pan.voucher.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.MessageDao;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.User;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message, Integer> implements MessageDao {

	@Override
	public List<Message> getSentMessagesForUser(final User user) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Message>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Message> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getMessagesForUser");
				query.setEntity("user", user);
				return query.list();
			}
		});
	}
	
}
