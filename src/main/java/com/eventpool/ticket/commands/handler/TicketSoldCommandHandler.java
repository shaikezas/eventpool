package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.entities.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketSoldCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketSoldCommandHandler implements CommandHandler<TicketSoldCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketSoldCommand command)
			throws Exception {
		return ticketDomainApi.updateTicketSold(command);
	}

}
