package org.pan.voucher.mapper;

import java.util.ArrayList;
import java.util.List;

import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.NonRegisteredUser;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserGroup;
import org.pan.voucher.regex.Extractor;
import org.pan.voucher.rest.model.InboxMessageRestModel;
import org.pan.voucher.rest.model.SentMessageRestModel;

public class MessageMapperService {
	
	public List<SentMessageRestModel> buildSentMessageListModel(List<Message> sentMessages) {
		
		List<SentMessageRestModel> sentMessageListModel = new ArrayList<SentMessageRestModel>();
		
		for (Message sentMsg : sentMessages) {
			sentMessageListModel.add(buildModelFromSentMessage(sentMsg));
		}
		
		return sentMessageListModel;
	}
	
	public List<InboxMessageRestModel> buildInboxMessageListModel(List<InboxMessage> inboxMessages) {
		
		List<InboxMessageRestModel> inboxMessageListModel = new ArrayList<InboxMessageRestModel>();
		
		for (InboxMessage inboxMsg : inboxMessages) {
			inboxMessageListModel.add(buildModelFromInboxMessage(inboxMsg));
		}
		
		return inboxMessageListModel;
	}
	
	public SentMessageRestModel buildModelFromSentMessage(Message message) {
		
		SentMessageRestModel sentMessageModel = new SentMessageRestModel();
		sentMessageModel.setDateSent(message.getSentDate());
		sentMessageModel.setMessagePayload(message.getText());
		
		List<String> messageToList = buildMessageToList(message);
		
		sentMessageModel.setMessageTo(messageToList);
	    return sentMessageModel;
	}

	public InboxMessageRestModel buildModelFromInboxMessage(InboxMessage inboxMessage) {
		
		InboxMessageRestModel inboxMessageModel = new InboxMessageRestModel();
		inboxMessageModel.setDateReceived(inboxMessage.getDateReceived());
		inboxMessageModel.setMessagePayload(inboxMessage.getMessagePayload().getText());
		inboxMessageModel.setSenderName(inboxMessage.getMessagePayload().getSender().getUserCredential().getUsername());
		inboxMessageModel.setIsRead(inboxMessage.isRead());
		inboxMessageModel.setMessageId(inboxMessage.getId());
	    return inboxMessageModel;
	}
	
	public List<String> extractNames(String message) {
		List<String> names;
	    Extractor extractor = new Extractor();

	    names = extractor.extractMentionedScreennames(message);
	    for (String name : names) {
	      System.out.println("Mentioned @" + name);
	    }
	    return names;
	}
	
	
	private List<String> buildMessageToList(Message message) {
		
		List<String> messageToList = new ArrayList<String>();
		
		for (NonRegisteredUser nonRegUser : message.getNonRegisteredUsers()) {
			messageToList.add(nonRegUser.getInternationalNumber());
		}
		
		for (User regUser : message.getRegisteredUsers()) {
			messageToList.add(regUser.getUserCredential().getUsername());
		}
		
		for (UserGroup userGroup : message.getUserGroups()) {
			messageToList.add(userGroup.getName());
		}
		
		return messageToList;
	}
}
