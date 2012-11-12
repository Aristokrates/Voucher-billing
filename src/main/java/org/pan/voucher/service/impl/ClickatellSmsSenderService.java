package org.pan.voucher.service.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.pan.voucher.service.SmsSenderService;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.TimeoutException;
import org.smslib.http.ClickatellHTTPGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("clickatellSmsSender")
public class ClickatellSmsSenderService implements SmsSenderService {
	
	@Resource private ClickatellHTTPGateway clickatellGateway;
	@Resource private org.smslib.Service smsService;
	
	@Value("${clickatell.callbackNumber}")
	private String callbackNumber;
	
	@PostConstruct
	public void init() throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException {
		smsService.addGateway(clickatellGateway);
		smsService.startService();
	}
	
	@PreDestroy
	public void destroy() throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException {
		smsService.stopService();
	}

	@Override
	public void sendSmsMessage(String messageText, String internationalNumber) {
		
		OutboundMessage msg = new OutboundMessage(internationalNumber, messageText);
		msg.setFrom(callbackNumber);
		try {
			System.out.println("Dummy sending: " + msg);
			//smsService.sendMessage(msg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

}
