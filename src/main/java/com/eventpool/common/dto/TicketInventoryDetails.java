package com.eventpool.common.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TicketInventoryDetails {
	private Long ticketId;
	private int invQty ;
	private int blockingQty;
	private boolean isInvUpdated=false;
	private boolean isInvBlocked=false;
	
	private int unBlockingQty;
	private boolean isInvUnBlocked=false;
	
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

	@XmlElement
	public int getBlockingQty() {
		return blockingQty;
	}

	public void setBlockingQty(int blockingQty) {
		this.blockingQty = blockingQty;
	}

	@XmlElement
	public boolean isInvBlocked() {
		return isInvBlocked;
	}

	public void setInvBlocked(boolean isInvBlocked) {
		this.isInvBlocked = isInvBlocked;
	}
	
	@XmlElement
	public int getUnBlockingQty() {
		return unBlockingQty;
	}

	public void setUnBlockingQty(int unBlockingQty) {
		this.unBlockingQty = unBlockingQty;
	}

	@XmlElement
	public boolean isInvUnBlocked() {
		return isInvUnBlocked;
	}

	public void setInvUnBlocked(boolean isInvUnBlocked) {
		this.isInvUnBlocked = isInvUnBlocked;
	}
	
	
	
}