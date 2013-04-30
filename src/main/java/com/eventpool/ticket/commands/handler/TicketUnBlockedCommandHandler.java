package com.eventpool.ticket.commands.handler;

import javax.annotation.Resource;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;
import com.eventpool.ticket.core.TicketDomainApi;



@CommandHandlerAnnotation
public class TicketUnBlockedCommandHandler implements CommandHandler<TicketUnBlockedCommand, TicketInventoryDetails>{
	
	@Resource
	TicketDomainApi ticketDomainApi;

	public TicketInventoryDetails handle(TicketUnBlockedCommand command)
			throws Exception {
		return ticketDomainApi.updateTicketUnBlocked(command);
	}

}
