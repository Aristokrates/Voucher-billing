package org.pan.voucher.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.TransactionDao;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.User;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("transactionDao")
public class TransactionDaoImpl extends BaseDaoImpl<Transaction, Integer> implements TransactionDao {

	@Override
	public List<Transaction> getTransactionsByUser(final User user) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Transaction>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Transaction> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getTransactionsByUser");
				query.setEntity("user", user);
				return query.list();
			}
		});
	}

	@Override
	public List<Transaction> getBoughtVoucherTransactionsByUser(final User user) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Transaction>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Transaction> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getBoughtVoucherTransactionsByUser");
				query.setEntity("user", user);
				return query.list();
			}
		});
	}

	@Override
	public List<Transaction> getTopupTransactionsByUser(final User user) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Transaction>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Transaction> doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.getNamedQuery("getTopupTransactionsByUser");
				query.setEntity("user", user);
				
				return query.list();
			}
		});
	}
}
