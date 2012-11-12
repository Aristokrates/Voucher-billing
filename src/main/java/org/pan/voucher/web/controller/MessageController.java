package org.pan.voucher.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.MessageMapperService;
import org.pan.voucher.model.InboxMessage;
import org.pan.voucher.model.Message;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.InboxMessageRestModel;
import org.pan.voucher.rest.model.SentMessageRestModel;
import org.pan.voucher.service.MessageService;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/message")
public class MessageController extends AbstractWebController {
	
	@Resource private MessageService messageService;
	@Resource private MessageMapperService messageMapper;
	
	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String showMessageHomePage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);
		
		return MessageViewPage.MESSAGE_HOME.getPageName(device);
	}
	
	@RequestMapping(value="/sent.do", method=RequestMethod.GET)
	public String showSentMessagePage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {
		
		User user = getUser();
		List<Message> sentMessages = messageService.getSentMessagesForUser(user);
		List<SentMessageRestModel> sentMessageModel = messageMapper.buildSentMessageListModel(sentMessages);
		modelMap.addAttribute("sentMessageList", sentMessageModel);
		
		return MessageViewPage.SENT_MESSAGE.getPageName(device);
	}
	
	@RequestMapping(value="/inbox.do", method=RequestMethod.GET)
	public String showInboxMessagePage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {
		
		User user = getUser();
		List<InboxMessage> inboxMessages = messageService.getInboxMessagesForUser(user);
		List<InboxMessageRestModel> inboxMessageModel = messageMapper.buildInboxMessageListModel(inboxMessages);
		modelMap.addAttribute("inboxMessageList", inboxMessageModel);
		
		return MessageViewPage.INBOX_MESSAGE.getPageName(device);
	}
	
	@RequestMapping(value="/compose.do", method=RequestMethod.GET)
	public String showComposeMessagePage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {
		
		return MessageViewPage.COMPOSE_MESSAGE.getPageName(device);
	}
	
	@RequestMapping(value="/compose.do", method=RequestMethod.POST)
	public String composeMessage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@RequestParam(value="message") String message,
			ModelMap modelMap) {
		
		List<String> names = messageMapper.extractNames(message);
	    
	    messageService.sendMessageFromApp(message, names, getUser());
		
		return "redirect:/" + MessageViewPage.MESSAGE_HOME.getActionUrl(device);
	
	}
	/**
	 * Page view names and action urls constants.
	 */
	public static enum MessageViewPage {
		
		MESSAGE_HOME("page.message.home", "message/home"),
		SENT_MESSAGE("page.sent.message", "message/sent"),
		COMPOSE_MESSAGE("page.compose.message", "message/compose"),
		INBOX_MESSAGE("page.inbox.message", "message/inbox");

		/**
		 * Enumeration value.
		 */
		private String pageName; // Correspondents to tiles mapping
		
		private String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private MessageViewPage(String pageName, String actionUrl) {
			this.pageName = pageName;
			this.actionUrl = actionUrl;
		}

		/**
		 * Gets the pageName.
		 *
		 * @return Returns the pageName.
		 */
		public String getPageName(String device) {
			return device + '.' + pageName;
		}
		
		public String getActionUrl(String device) {
			return device + '/' + actionUrl;
		}
	};

}
