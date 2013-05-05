package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketCreatedCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketCreatedCommandHandler implements CommandHandler<TicketCreatedCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketCreatedCommand command)
			throws Exception {
		return ticketDomainApi.ticketCreated(command);
	}

}
