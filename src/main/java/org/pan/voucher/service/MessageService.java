package org.pan.voucher.service;

import java.util.List;

import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.User;

public interface MessageService {
	
	void sendMessageFromApp(String text, List<String> messageTo, User sender);
	
	void sendMessageFromPhone(String text, List<String> messageTo, String nonRegisteredSender);
	
	void markAsRead(Integer messageId);
	
	List<Message> getSentMessagesForUser(User user);
	
	List<InboxMessage> getInboxMessagesForUser(User user);
	
	

}
