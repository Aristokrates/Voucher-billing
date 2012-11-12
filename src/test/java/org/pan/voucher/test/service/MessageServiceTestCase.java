package org.pan.voucher.test.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.User;
import org.pan.voucher.service.MessageService;
import org.pan.voucher.service.UserGroupService;
import org.pan.voucher.service.UserService;
import org.pan.voucher.test.BaseTestCase;

public class MessageServiceTestCase extends BaseTestCase {
	
	@Resource private MessageService messageService;
	@Resource private UserService userService;
	@Resource private UserGroupService userGroupService;
	
	@Test
	public void testSendMessage() {
		User user = userService.getUserById(5);
		messageService.sendMessageFromApp("I love you", Arrays.asList("+38970351660"), user);
	}
	
	@Test
	public void testGetInboxSentMessage() {

		User user = userService.getUserById(1);
		List<InboxMessage> inboxMessages = messageService.getInboxMessagesForUser(user);
		System.out.println(inboxMessages.size());
	}
	
	@Test
	public void testGetSentMessage() {

		User user = userService.getUserById(1);
		List<Message> messages = messageService.getSentMessagesForUser(user);
		System.out.println(messages.size());
	}

}
