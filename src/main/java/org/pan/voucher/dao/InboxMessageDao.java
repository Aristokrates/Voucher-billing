package org.pan.voucher.dao;

import java.util.List;

import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.User;

public interface InboxMessageDao extends BaseDao<InboxMessage, Integer> {

	List<InboxMessage> getInboxMessagesForUser(User user);
	
	void markAsRead(Integer messageId);

}
