package org.pan.voucher.test;

import java.io.IOException;

import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.http.ClickatellHTTPGateway;

public class SmsMessageTest {

	public static void main(String[] args) throws IOException, InterruptedException, SMSLibException {
		OutboundMessage msg;
		System.out.println("Example: Send message from Clickatell HTTP Interface.");
		System.out.println(Library.getLibraryDescription());
		System.out.println("Version: " + Library.getLibraryVersion());
		ClickatellHTTPGateway gateway = new ClickatellHTTPGateway("clickatell.http.1", "3323002", "createdisrupt", "m1k3sw3bpl@yb0x");
		gateway.setOutbound(true);
		// Do we need secure (https) communication?
		// True uses "https", false uses "http" - default is false.
		gateway.setSecure(true);
		Service.getInstance().addGateway(gateway);
		Service.getInstance().startService();
		// Create a message.
		msg = new OutboundMessage("+38970563236", "Hello from SMSLib (Clickatell handler) again");
		msg.setFrom("+31621627197");
		// Ask for coverage.
		System.out.println("Is recipient's network covered? : " + gateway.queryCoverage(msg));
		// Send the message.
		Service.getInstance().sendMessage(msg);
		System.out.println(msg);
		// Now query the service to find out our credit balance.
		System.out.println("Remaining credit: " + gateway.queryBalance());
		// Send some messages in async mode...
		// After this, your IOutboundMessageNotification method will be called
		// for each message sent out.
		//msg = new OutboundMessage("+30...", "Max");
		//srv.queueMessage(msg, "clickatell.http.1", AbstractGateway.Priority.HIGH);
		//msg = new OutboundMessage("+30...", "Min");
		//srv.queueMessage(msg, "clickatell.http.1", AbstractGateway.Priority.LOW);
		System.out.println("Now Sleeping - Hit <enter> to terminate.");
		System.in.read();
		Service.getInstance().stopService();
	}

	public class OutboundNotification implements IOutboundMessageNotification
	{
		public void process(AGateway gateway, OutboundMessage msg)
		{
			System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
			System.out.println(msg);
		}
	}

}
