package com.eventpool.ticket.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("serial")
@Command
@XmlRootElement
public class TicketCreatedCommand extends AbstractICommand {
	
	@NotNull
	private Long ticketId;
	
	@NotNull
	@Min(1)
	private Integer maxQty;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}

	
	
}
