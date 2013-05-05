package com.eventpool.ticket.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.eventpool.common.commands.AbstractICommand;
import com.eventpool.common.commands.Command;


@SuppressWarnings("serial")
@Command
@XmlRootElement
public class TicketUnBlockedCommand extends AbstractICommand {
	
	@NotNull
	private Long ticketId;
	
	@NotNull
	@Min(1)
	private Integer unBlockingQty;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getUnBlockingQty() {
		return unBlockingQty;
	}

	public void setUnBlockingQty(Integer unBlockingQty) {
		this.unBlockingQty = unBlockingQty;
	}

	
	
}
