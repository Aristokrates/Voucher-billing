package org.pan.voucher.rest.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class InboxMessageRestModel extends MessageRestModel {
	
	@JsonProperty
	private String senderName;
	
	@JsonProperty
	private Date dateReceived;
	
	@JsonProperty
	private Boolean isRead;
	
	@JsonProperty
	private Integer messageId;
	
	public InboxMessageRestModel() {
		super("Inbox");
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	
}
