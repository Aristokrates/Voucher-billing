package org.pan.voucher.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="inbox_message")
@NamedQueries({
	@NamedQuery(name="getInboxMessagesForUser", query="select distinct i from InboxMessage i left join i.inboxUser left join fetch i.messagePayload m left join fetch m.sender s where i.inboxUser=:user order by i.dateReceived desc, i.isRead"),
	@NamedQuery(name="markAsRead", query="update InboxMessage m set m.isRead=true where m.id=:messageId")
})
public class InboxMessage extends BaseEntity {

	private static final long serialVersionUID = 4381941897409222413L;

	@Column(name="is_read")
	private boolean isRead;
	
	@Column(name="date_received")
	private Date dateReceived;
	
	@ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinColumn(name="inbox_user_id")
	private User inboxUser;
	
	@ManyToOne(targetEntity=Message.class, fetch=FetchType.LAZY)
	@JoinColumn(name="message_id")
	private Message messagePayload;

	public InboxMessage() {
		super();
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public User getInboxUser() {
		return inboxUser;
	}

	public void setInboxUser(User inboxUser) {
		this.inboxUser = inboxUser;
	}

	public Message getMessagePayload() {
		return messagePayload;
	}

	public void setMessagePayload(Message messagePayload) {
		this.messagePayload = messagePayload;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	
}
