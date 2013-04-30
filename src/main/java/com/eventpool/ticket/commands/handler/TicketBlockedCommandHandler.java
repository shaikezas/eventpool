package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketBlockedCommandHandler implements CommandHandler<TicketBlockedCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketBlockedCommand command)
			throws Exception {
		return ticketDomainApi.updateTicketBlocked(command);
	}

}
