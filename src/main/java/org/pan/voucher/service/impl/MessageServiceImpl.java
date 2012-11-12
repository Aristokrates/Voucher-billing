package org.pan.voucher.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.pan.voucher.dao.InboxMessageDao;
import org.pan.voucher.dao.MessageDao;
import org.pan.voucher.dao.NonRegisteredUserDao;
import org.pan.voucher.dao.SmsCostDao;
import org.pan.voucher.dao.TransactionDao;
import org.pan.voucher.dao.UserDao;
import org.pan.voucher.dao.UserGroupDao;
import org.pan.voucher.exception.UserNotEligibleToBuyException;
import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.NonRegisteredUser;
import org.pan.voucher.model.SmsCost;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.TransactionType;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserGroup;
import org.pan.voucher.service.MessageService;
import org.pan.voucher.service.SmsSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Service("messageService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class MessageServiceImpl implements MessageService {

	@Resource private MessageDao messageDao;
	@Resource private UserDao userDao;
	@Resource private UserGroupDao userGroupDao;
	@Resource private InboxMessageDao inboxMessageDao;
	@Resource private SmsCostDao smsCostDao;
	@Resource private NonRegisteredUserDao nonRegisteredUserDao;
	@Resource private SmsSenderService smsSender;
	@Resource private TransactionDao txnDao;
	
	@Resource private PhoneNumberUtil phoneNumberUtil;
	
	@Value("${sms.defaultRegistrationText}")
	private String defaultRegistrationSms;
	
	private static BigDecimal defaultSmsCost = BigDecimal.valueOf(2.00);
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void sendMessageFromApp(String text, List<String> messageTo, User sender) {
		sendMessage(text, messageTo, sender, null, false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void sendMessageFromPhone(String text, List<String> messageTo, String nonRegisteredSender) {
		sendMessage(text, messageTo, null, nonRegisteredSender, true);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	private void sendMessage(String text, List<String> messagesTo, User sender, String nonRegisteredSender, boolean fromPhone) {

		Message message = new Message();
		message.setText(text);
		message.setSentDate(new Date());
		message.setSender(sender);
		message.setNonRegisteredSender(nonRegisteredSender);

		for (String messageTo : messagesTo) {

			User user = userDao.findUserByUsername(messageTo);
			if (user != null) {
				message.addRegisteredUser(user);
				continue;
			}

			UserGroup userGroup = userGroupDao.getUserGroupByName(messageTo);
			if (userGroup != null) {
				message.addUserGroup(userGroup);
				continue;
			}
			
			PhoneNumber numero = isMobileNumber(messageTo);
			
			if (numero != null) {
				NonRegisteredUser nonRegisteredUser = nonRegisteredUserDao.getNonRegisteredUserByNumber(numero.getNationalNumber(), numero.getCountryCode());
				if (nonRegisteredUser == null) {
					nonRegisteredUser = new NonRegisteredUser(numero.getNationalNumber(), numero.getCountryCode(), false);
					nonRegisteredUserDao.save(nonRegisteredUser);
				}
				message.addNonRegisteredUser(nonRegisteredUser);
			}
		}

		messageDao.save(message);

		createInboxMessageToRegisteredUsers(message, message.getRegisteredUsers());		
		createInboxMessageForUserGroupMembers(message, fromPhone);
		if (!fromPhone) {
			sendSmsToNonRegisteredUsers(message, message.getNonRegisteredUsers());		
		}
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void markAsRead(Integer messageId) {
		
		inboxMessageDao.markAsRead(messageId);
	}

	@Override
	public List<Message> getSentMessagesForUser(User user) {
		return messageDao.getSentMessagesForUser(user);
	}

	@Override
	public List<InboxMessage> getInboxMessagesForUser(User user) {
		return inboxMessageDao.getInboxMessagesForUser(user);
	}


	private void createInboxMessageForUserGroupMembers(Message message, boolean fromPhone) {

		for (UserGroup userGroup : message.getUserGroups()) {
			createInboxMessageToRegisteredUsers(message, new HashSet<User>(userGroup.getGroupMembers()));
			if (!fromPhone) {
				sendSmsToNonRegisteredUsers(message, userGroup.getNonRegisteredGroupMembers());
			}
		}
	}

	private void createInboxMessageToRegisteredUsers(Message message, Set<User> users) {

		for (User registeredUser : users) {
			InboxMessage inboxMessage = new InboxMessage();
			inboxMessage.setInboxUser(registeredUser);
			inboxMessage.setMessagePayload(message);
			inboxMessage.setRead(false);
			inboxMessage.setDateReceived(new Date());
			inboxMessageDao.save(inboxMessage);
		}
	}

	private void sendSmsToNonRegisteredUsers(Message message, Set<NonRegisteredUser> nonRegisteredUsers) {

		User sender = message.getSender();

		BigDecimal balance = sender.getBalance();
		BigDecimal charge;

		try {
			charge = checkBalance(balance, nonRegisteredUsers);		
		} catch (Exception e) {			
			//TODO  throw diff kind of exception
			throw new UserNotEligibleToBuyException();
		}
		
		createTransaction(sender, charge);

		for (NonRegisteredUser nonRegisteredUser : nonRegisteredUsers) {	
			//FIXME(pai) do we send message text or default registration text here
			smsSender.sendSmsMessage(message.getText(), nonRegisteredUser.getInternationalNumber());
		}
	}

	private void createTransaction(User user, BigDecimal charge) {
			
		user.setBalance(user.getBalance().subtract(charge));
		userDao.save(user);
		
		Transaction trans = new Transaction();
		trans.setUser(user);
		trans.setCredit(charge);
		trans.setBalance(user.getBalance());
		trans.setDateCreated(new Date());
		trans.setLastModifiedDate(new Date());
		trans.setTransationType(TransactionType.Sms_sent_charge);
		txnDao.save(trans);		
		
	}

	private BigDecimal checkBalance(BigDecimal balance, Set<NonRegisteredUser> nonRegisteredUsers) throws UserNotEligibleToBuyException {
		
		BigDecimal charge = BigDecimal.valueOf(0);
		
		for (NonRegisteredUser nonRegUser : nonRegisteredUsers) {
			
			BigDecimal singleSmsCost;
			
			Integer countryCode = nonRegUser.getCountryCode();
			SmsCost cost = smsCostDao.getSmsCostByCountryCode(countryCode);
			if (cost == null) {
				singleSmsCost = defaultSmsCost;
			} else {
				singleSmsCost = cost.getSmsCost();
			}
			charge = charge.add(singleSmsCost);
		}
		
		if (balance.compareTo(charge) == -1) {
			throw new UserNotEligibleToBuyException();
		}
		
		return charge;
	}

	private PhoneNumber isMobileNumber(String messageTo) {

		try {
			return phoneNumberUtil.parse(messageTo, "FR");
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
