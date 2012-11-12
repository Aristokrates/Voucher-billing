package org.pan.voucher.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.PaymentMapperService;
import org.pan.voucher.rest.model.AmountModel;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/payment")
public class PaymentController extends AbstractWebController {
	
	@Resource private PaymentMapperService paymentMapperService;
	
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String showPaymentPage(HttpServletRequest request, @RequestParam(value = "device", required = true) String device, HttpSession session, ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);
		
		List<AmountModel> amountList = Arrays.asList(AmountModel.values());
		modelMap.addAttribute("amountList", amountList);
		
		return PaymentViewPage.PAYMENT_HOME.getPageName(device);		
		
	}
	
	@RequestMapping(value = "/pay.do", method = RequestMethod.POST)
	public String pay (HttpServletRequest request, @RequestParam(value = "amount", required=true) Integer amount, HttpSession session, ModelMap modelMap) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
		
		if (amount == null) {
			throw new IllegalArgumentException();
		}
		
		Integer userId = getUserId();
		
		StringBuffer buf = new StringBuffer(paymentMapperService.getPayFastUrl());	
		buf.append("?");
		
		buf.append("amount=" + amount);		
		buf.append("&");
		
		buf.append("item_name=voucher");		
		buf.append("&");
		
		buf.append("merchant_id=" + paymentMapperService.getMerchantId());
		buf.append("&");
		
		buf.append("merchant_key=" + paymentMapperService.getMerchantKey());
		buf.append("&");
		
		buf.append("custom_int1=" + userId);
		buf.append("&");	
		
		URL url1 = new URL(paymentMapperService.getReturnUrl());
		String encodedurl1 = URLEncoder.encode(url1.toString(),"UTF-8"); 
		
		buf.append("return_url=" + encodedurl1);
		buf.append("&");
		
		URL url2 = new URL(paymentMapperService.getNotifyUrl());
		String encodedurl2 = URLEncoder.encode(url2.toString(),"UTF-8"); 
		
		buf.append("notify_url=" + encodedurl2);
		
		return "redirect:" + buf.toString();
		
	}
	
	/**
	 * Page view names and action urls constants.
	 */
	public static enum PaymentViewPage {
		
		PAYMENT_HOME("page.payment", "payment");

		/**
		 * Enumeration value.
		 */
		private final String pageName; // Correspondents to tiles mapping
		
		private final String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private PaymentViewPage(String pageName, String actionUrl) {
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
