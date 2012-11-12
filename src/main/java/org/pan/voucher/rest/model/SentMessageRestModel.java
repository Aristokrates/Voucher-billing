package org.pan.voucher.rest.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SentMessageRestModel extends MessageRestModel {
	
	@JsonProperty
	private List<String> messageTo;
	
	@JsonProperty
	private Date dateSent;

	public SentMessageRestModel() {
		super("Sent");
	}

	public List<String> getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(List<String> messageTo) {
		this.messageTo = messageTo;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
}
