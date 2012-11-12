package org.pan.voucher.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.TransactionMapperService;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.TransactionRestModel;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserService;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/transaction")
public class TransactionController extends AbstractWebController {
	
	@Resource private TransactionService transactionService;
	@Resource private UserService userService;
	@Resource private TransactionMapperService transactionMapper;
	
	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String showTransactionPage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);
		
		return TransactionViewPage.TRANSACTION_HOME.getPageName(device);
	}
	
	@RequestMapping(value="/filter.do")
	public @ResponseBody List<TransactionRestModel> filterTransactions(HttpSession session, @RequestParam(value = "keyword", required = true) String keyword,
			ModelMap modelMap) {
		
		User user = userService.getUserById(getUserId());
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		if (keyword.equals("all")) {
			transactions = transactionService.getTransactionsByUser(user);
		}
		
		if (keyword.equals("purchase")) {
			transactions = transactionService.getBoughtVoucherTransactionsByUser(user);
		}
		
		if (keyword.equals("topup")) {
			transactions = transactionService.getTopupTransactionsByUser(user);
		}
		
		return transactionMapper.buildTransactionListModel(transactions);
		
	}
	
	/**
	 * Page view names and action urls constants.
	 */
	public static enum TransactionViewPage {
		
		TRANSACTION_HOME("page.transaction", "transaction");

		/**
		 * Enumeration value.
		 */
		private final String pageName; // Correspondents to tiles mapping
		
		private final String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private TransactionViewPage(String pageName, String actionUrl) {
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
