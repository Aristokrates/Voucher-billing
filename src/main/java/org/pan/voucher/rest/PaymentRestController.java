package org.pan.voucher.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.PaymentModel;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/payment")
public class PaymentRestController {
	
	@Resource private UserService userService;
	@Resource private TransactionService txnService;
	
	@RequestMapping(value="/postprocess", method = RequestMethod.POST)
	public @ResponseBody String postprocessMessage(@ModelAttribute PaymentModel paymentModel, HttpServletRequest request, HttpServletResponse response, HttpSession session) {				
		
		String status = paymentModel.getPayment_status();
		
		if (!"COMPLETE".equals(status)) {
			throw new IllegalArgumentException("Payment not completed");
		}
		
		//TODO Check if the txn has been processed
		
		Integer userId = paymentModel.getCustom_int1();
		if (userId == null) {
			throw new InvalidDataException("User id not specified");
		}
				
		User user = userService.getUserById(userId);
		
		txnService.topUpAccount(user, paymentModel.getAmount_net());
		
		return "VERIFIED";	
	}
}
