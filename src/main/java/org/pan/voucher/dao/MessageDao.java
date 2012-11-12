package org.pan.voucher.dao;

import java.util.List;

import org.pan.voucher.model.Message;
import org.pan.voucher.model.User;

public interface MessageDao extends BaseDao<Message, Integer> {

	List<Message> getSentMessagesForUser(User user);

}
