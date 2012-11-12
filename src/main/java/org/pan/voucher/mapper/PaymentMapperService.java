package org.pan.voucher.mapper;

import org.springframework.beans.factory.annotation.Value;

public class PaymentMapperService {
	
	@Value("${payfastUrl}")
	private String payFastUrl;
	
	@Value("${notifyUrl}")
	private String notifyUrl;
	
	@Value("${returnUrl}")
	private String returnUrl;
	
	@Value("${merchantId}")
	private String merchantId;
	
	@Value("${merchantKey}")
	private String merchantKey;

	public PaymentMapperService() {
		super();
	}

	public String getPayFastUrl() {
		return payFastUrl;
	}

	public void setPayFastUrl(String payFastUrl) {
		this.payFastUrl = payFastUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}
}
