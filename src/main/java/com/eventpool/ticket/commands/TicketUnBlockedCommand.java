package com.eventpool.ticket.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("serial")
@Command
@XmlRootElement
public class TicketUnBlockedCommand extends AbstractICommand {
	
	@NotNull
	private Long ticketId;
	
	@NotNull
	@Min(1)
	private Integer blockingQty;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getBlockingQty() {
		return blockingQty;
	}
	
	public void setBlockingQty(Integer blockingQty) {
		this.blockingQty = blockingQty;
	}
	
	
}
