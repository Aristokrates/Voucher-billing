package org.pan.voucher.rest;

import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.mapper.TransactionMapperService;
import org.pan.voucher.model.Transaction;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.TransactionRestModel;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserSessionService;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionRestController extends AbstractWebController {
	
	@Resource private UserSessionService userSessionService;
	@Resource private TransactionService transactionService;
	@Resource private TransactionMapperService transactionMapper;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody List<TransactionRestModel> listTransactions(@RequestParam("sessionToken") String sessionToken) {
		
		Assert.notNull(sessionToken);
		User user = userSessionService.getUserForSession(sessionToken);
		
		List<Transaction> transactions = transactionService.getTransactionsByUser(user);
		
		return transactionMapper.buildTransactionListModel(transactions);
	}
}
