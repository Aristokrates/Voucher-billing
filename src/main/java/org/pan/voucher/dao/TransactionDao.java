package org.pan.voucher.dao;

import java.util.List;

import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.User;

public interface TransactionDao extends BaseDao<Transaction, Integer> {

	List<Transaction> getTransactionsByUser(User user);

	List<Transaction> getBoughtVoucherTransactionsByUser(User user);
	
	List<Transaction> getTopupTransactionsByUser(User user);

}
