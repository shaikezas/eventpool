package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketUpdatedCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketUpdatedCommandHandler implements CommandHandler<TicketUpdatedCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketUpdatedCommand command)
			throws Exception {
		return ticketDomainApi.ticketUpdated(command);
	}

}
