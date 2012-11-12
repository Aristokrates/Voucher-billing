package org.pan.voucher.service;

public interface SmsSenderService {

	void sendSmsMessage(String messageText, String internationalNumber);

}
