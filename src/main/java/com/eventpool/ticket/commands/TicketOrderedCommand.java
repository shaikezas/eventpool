package com.eventpool.ticket.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.eventpool.common.commands.AbstractICommand;
import com.eventpool.common.commands.Command;


@SuppressWarnings("serial")
@Command
@XmlRootElement
public class TicketOrderedCommand extends AbstractICommand {
	
	@NotNull
	private Long ticketId;
	
	@NotNull
	@Min(1)
	private Integer qty;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	
}
