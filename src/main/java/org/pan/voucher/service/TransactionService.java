package org.pan.voucher.service;

import java.math.BigDecimal;
import java.util.List;

import org.pan.voucher.exception.UserNotEligibleToBuyException;
import org.pan.voucher.exception.VoucherNotFoundException;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.User;
import org.pan.voucher.remoting.model.Voucher;

public interface TransactionService {

	Voucher buyProduct(User user, Integer productId) throws UserNotEligibleToBuyException, VoucherNotFoundException;

	List<Transaction> getTransactionsByUser(User user);
	
	List<Transaction> getBoughtVoucherTransactionsByUser(User user);
	
	List<Transaction> getTopupTransactionsByUser(User user);
	
	void topUpAccount(User popupedUser, BigDecimal ammount);
}
