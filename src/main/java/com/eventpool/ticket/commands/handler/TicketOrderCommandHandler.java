package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.entities.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketOrderCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketOrderCommandHandler implements CommandHandler<TicketOrderCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketOrderCommand command)
			throws Exception {
		return ticketDomainApi.updateTicketOrder(command);
	}

}
