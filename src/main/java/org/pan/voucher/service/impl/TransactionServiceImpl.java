package org.pan.voucher.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.dao.InboxMessageDao;
import org.pan.voucher.dao.MessageDao;
import org.pan.voucher.dao.ProductDao;
import org.pan.voucher.dao.TransactionDao;
import org.pan.voucher.dao.UserDao;
import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.exception.UserNotEligibleToBuyException;
import org.pan.voucher.exception.VoucherNotFoundException;
import org.pan.voucher.model.BoughtVoucher;
import org.pan.voucher.model.DigitalProduct;
import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.Subscription;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.TransactionType;
import org.pan.voucher.model.User;
import org.pan.voucher.remoting.VoucherRemoteService;
import org.pan.voucher.remoting.model.Voucher;
import org.pan.voucher.service.EmailSenderService;
import org.pan.voucher.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("transactionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TransactionServiceImpl implements TransactionService {
	
	@Resource private UserDao userDao;
	@Resource private TransactionDao transactionDao;
	@Resource private ProductDao productDao;
	@Resource private VoucherRemoteService remoteService;
	@Resource private MessageDao messageDao;
	@Resource private InboxMessageDao inboxMessageDao;
	
	@Resource private EmailSenderService emailSenderService;
	
	@Value("${bought.voucer.message}")
	private String boughtVoucherMessage;
	
	@Override
	public Voucher buyProduct(User user, Integer productId) throws UserNotEligibleToBuyException, VoucherNotFoundException {

		Product product = productDao.getProductById(productId);
		
		if (product == null) {
			throw new InvalidDataException();
		}
		
		synchronized (user) {
			
			// check eligibility
			boolean isEligible = checkBalance(product, user);
			
			if (!isEligible) {
				throw new UserNotEligibleToBuyException(user.getUserCredential().getUsername(), product.getName());
			}
			
			Voucher voucher = null;
			
			if (product.getDigitalProduct() != null) {
				DigitalProduct digitalProduct = product.getDigitalProduct();
				voucher = new Voucher(digitalProduct.getPin(), digitalProduct.getSerial(), digitalProduct.getCostPrice().floatValue());
			} else {				
				// get the voucher from vendor Ws
				voucher = remoteService.getVoucher(product.getVendor().getName().trim().toLowerCase(), product.getSalesPrice().intValue());
			}
			
			
			// update user balance
			boolean doubleisEligibleCheck = checkBalance(product, user);
			if (!doubleisEligibleCheck) {
				throw new UserNotEligibleToBuyException(user.getUserCredential().getUsername(), product.getName());
			}
			
			BigDecimal tranfee = BigDecimal.valueOf(0);
			Subscription subscripion = user.getUserSubscription();
			
			if (subscripion == null || !subscripion.isValid()) {
				tranfee = product.getTransfee();
			}
			
			BigDecimal userPaidPrice = product.getCostPrice().add(tranfee);
			user.setBalance(user.getBalance().subtract(userPaidPrice));
			userDao.save(user);			
			
			//create txn
			Transaction trans = new Transaction();
			trans.setProduct(product);
			trans.setBalance(user.getBalance());
			trans.setDebit(userPaidPrice);
			trans.setMobileNumber(user.getUserCredential().getMobile());
			trans.setAmount(BigDecimal.valueOf(voucher.getCostprice()));
			trans.setDateCreated(new Date());
			trans.setLastModifiedDate(new Date());
			trans.setTransationType(TransactionType.Purchase);
			trans.setUser(user);		
			BoughtVoucher boughtVoucher = new BoughtVoucher(voucher.getPin(), voucher.getSerial(), trans, product.getVendor());
			trans.addBoughtVoucher(boughtVoucher);
			transactionDao.save(trans);			
			
			String messageToSend = String.format(boughtVoucherMessage, voucher.getPin(), voucher.getSerial());
			//send messages when the voucher has been bought			
			Message message = new Message();
			message.setText(messageToSend);
			message.setSentDate(new Date());
			message.setSender(user);
			message.addRegisteredUser(user);
			messageDao.save(message);
			
			InboxMessage inboxMessage = new InboxMessage();
			inboxMessage.setInboxUser(user);
			inboxMessage.setMessagePayload(message);
			inboxMessage.setRead(false);
			inboxMessage.setDateReceived(new Date());
			inboxMessageDao.save(inboxMessage);
			
			//send email
			if (user.getUserProfile().getEmail() != null) {
				//send email to address
				String emailAddr = user.getUserProfile().getEmail();
				emailSenderService.sendVoucherBoughtEmail(messageToSend, emailAddr);
			}
			
			return voucher;
			
		}		
	}

	@Override
	public void topUpAccount(User topupedUser, BigDecimal ammount) {

		topupedUser.setBalance(topupedUser.getBalance().add(ammount));
		userDao.save(topupedUser);
		
		Transaction trans = new Transaction();
		trans.setUser(topupedUser);
		trans.setCredit(ammount);
		trans.setBalance(topupedUser.getBalance());
		trans.setDateCreated(new Date());
		trans.setLastModifiedDate(new Date());
		trans.setTransationType(TransactionType.Credit_Top_up);
		transactionDao.save(trans);		
	}

	@Override
	public List<Transaction> getTransactionsByUser(User user) {
		return transactionDao.getTransactionsByUser(user);
	}
	
	
	@Override
	public List<Transaction> getBoughtVoucherTransactionsByUser(User user) {
		return transactionDao.getBoughtVoucherTransactionsByUser(user);
	}
	
	@Override
	public List<Transaction> getTopupTransactionsByUser(User user) {
		return transactionDao.getTopupTransactionsByUser(user);
	}

	private boolean checkBalance(Product product, User user) {
		
		BigDecimal userPaidPrice = product.getCostPrice().add(product.getTransfee());
		
		if (user.getBalance().compareTo(userPaidPrice) == -1) {
			return false;
		}
		return true;
	}
}
