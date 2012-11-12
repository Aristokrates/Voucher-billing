package org.pan.voucher.service;

public interface EmailSenderService {
	
	void sendVoucherBoughtEmail(String text, String to);

}
