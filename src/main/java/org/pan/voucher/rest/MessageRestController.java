package org.pan.voucher.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.MessageMapperService;
import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.InboxMessageRestModel;
import org.pan.voucher.rest.model.SentMessageRestModel;
import org.pan.voucher.rest.model.SmsSyncInputModel;
import org.pan.voucher.rest.model.SmsSyncPayload;
import org.pan.voucher.rest.model.SmsSyncStatusModel;
import org.pan.voucher.service.MessageService;
import org.pan.voucher.service.UserSessionService;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="/message")
public class MessageRestController extends AbstractWebController {
	
	@Resource private MessageService messageService;
	@Resource private UserSessionService userSessionService;
	@Resource private MessageMapperService messageMapperService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value="/compose", method=RequestMethod.POST)
	public void sendMessage(@RequestBody SentMessageRestModel messageRestModel, 
			@RequestParam("sessionToken") String sessionToken) {
		
		User user = userSessionService.getUserForSession(sessionToken);
		List<String> names = messageMapperService.extractNames(messageRestModel.getMessagePayload());
		messageService.sendMessageFromApp(messageRestModel.getMessagePayload(), names, user);
	}
	
	@RequestMapping(value="/sent", method=RequestMethod.GET)
	public @ResponseBody List<SentMessageRestModel> getSentMessages(@RequestParam("sessionToken") String sessionToken) {
		
		User user = userSessionService.getUserForSession(sessionToken);
		List<Message> sentMessages = messageService.getSentMessagesForUser(user);
		return messageMapperService.buildSentMessageListModel(sentMessages);
	}
	
	@RequestMapping(value="/inbox", method=RequestMethod.GET)
	public @ResponseBody List<InboxMessageRestModel> getInboxMessages(@RequestParam("sessionToken") String sessionToken) {
		
		User user = userSessionService.getUserForSession(sessionToken);
		List<InboxMessage> inboxMessages = messageService.getInboxMessagesForUser(user);
		return messageMapperService.buildInboxMessageListModel(inboxMessages);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value="/read", method=RequestMethod.POST)
	public void markMessageAsRead(@RequestParam("sessionToken") String sessionToken, @RequestParam("messageId") Integer messageId) {
		
		userSessionService.getUserForSession(sessionToken);
		messageService.markAsRead(messageId);
		
	}
	
	@RequestMapping(value="/postprocess", method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
	public @ResponseBody SmsSyncStatusModel postprocessMessage(@ModelAttribute SmsSyncInputModel smsInput, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {		
		
		List<String> names = messageMapperService.extractNames(smsInput.getMessage());
		
		messageService.sendMessageFromPhone(smsInput.getMessage(), names, smsInput.getFrom());
		
		return new SmsSyncStatusModel(new SmsSyncPayload("true"));	
	}
}
