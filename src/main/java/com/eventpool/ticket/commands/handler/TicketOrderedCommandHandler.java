package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketOrderedCommandHandler implements CommandHandler<TicketOrderedCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketOrderedCommand command)
			throws Exception {
		return ticketDomainApi.updateTicketOrdered(command);
	}

}
