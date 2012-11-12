package org.pan.voucher.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MessageRestModel {
	
	@JsonProperty
	private String messagePayload;
	
	@JsonProperty
	private String folder;

	public MessageRestModel() {
		super();
	}
	
	public MessageRestModel(String folder) {
		this(null, folder);
	}

	public MessageRestModel(String messagePayload, String folder) {
		super();
		this.messagePayload = messagePayload;
		this.folder = folder;
	}

	public String getMessagePayload() {
		return messagePayload;
	}

	public void setMessagePayload(String messagePayload) {
		this.messagePayload = messagePayload;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
	
}
