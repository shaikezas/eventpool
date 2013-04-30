package com.eventpool.common.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TicketInventoryDetails {
	private Long ticketId;
	private int invQty ;
	private int blockingQty;
	private boolean isInvUpdated;
	private boolean isInvBlocked;
	
	@XmlElement
	public Long getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	
	@XmlElement
	public int getInvQty() {
		return invQty;
	}
	public void setInvQty(int invQty) {
		this.invQty = invQty;
	}
	
	@XmlElement
	public boolean isInvUpdated() {
		return isInvUpdated;
	}
	public void setInvUpdated(boolean isInvUpdated) {
		this.isInvUpdated = isInvUpdated;
	}

	public int getBlockingQty() {
		return blockingQty;
	}

	public void setBlockingQty(int blockingQty) {
		this.blockingQty = blockingQty;
	}

	public boolean isInvBlocked() {
		return isInvBlocked;
	}

	public void setInvBlocked(boolean isInvBlocked) {
		this.isInvBlocked = isInvBlocked;
	}
	
	
}