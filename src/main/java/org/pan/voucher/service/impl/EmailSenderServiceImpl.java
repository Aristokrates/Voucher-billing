package org.pan.voucher.service.impl;

import org.pan.voucher.service.EmailSenderService;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailSenderServiceImpl implements EmailSenderService {
	
	private MailSender mailSender;
	
	private SimpleMailMessage templateMessage;

	@Override
	public void sendVoucherBoughtEmail(String text, String to) {
		
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(to);
        msg.setText(text);
        msg.setSubject("VENDME: Voucher bought");
        try{
            this.mailSender.send(msg);
        }
        catch(MailException ex) {
            System.err.println(ex.getMessage());            
        }
		
	}
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTemplateMessage(SimpleMailMessage templateMail) {
		this.templateMessage = templateMail;
	}
	
}
