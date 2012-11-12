package org.pan.voucher.rest.model;

import java.math.BigDecimal;

public class PaymentModel {
	
	private Integer m_payment_id;
	
	private Integer pf_payment_id;
	
	private String payment_status;
	
	private String item_name;
	
	private String item_description;
	
	private BigDecimal amount_gross;
	
	private BigDecimal amount_fee;
	
	private BigDecimal amount_net;
	
	private Integer custom_int1;

	public PaymentModel() {
		super();
	}

	public Integer getM_payment_id() {
		return m_payment_id;
	}

	public void setM_payment_id(Integer mPaymentId) {
		m_payment_id = mPaymentId;
	}

	public Integer getPf_payment_id() {
		return pf_payment_id;
	}

	public void setPf_payment_id(Integer pfPaymentId) {
		pf_payment_id = pfPaymentId;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String paymentStatus) {
		payment_status = paymentStatus;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String itemName) {
		item_name = itemName;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String itemDescription) {
		item_description = itemDescription;
	}

	public BigDecimal getAmount_gross() {
		return amount_gross;
	}

	public void setAmount_gross(BigDecimal amountGross) {
		amount_gross = amountGross;
	}

	public BigDecimal getAmount_fee() {
		return amount_fee;
	}

	public void setAmount_fee(BigDecimal amountFee) {
		amount_fee = amountFee;
	}

	public BigDecimal getAmount_net() {
		return amount_net;
	}

	public void setAmount_net(BigDecimal amountNet) {
		amount_net = amountNet;
	}

	public Integer getCustom_int1() {
		return custom_int1;
	}

	public void setCustom_int1(Integer customInt1) {
		custom_int1 = customInt1;
	}
}
