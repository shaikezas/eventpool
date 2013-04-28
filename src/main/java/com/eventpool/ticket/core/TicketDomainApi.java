package com.eventpool.ticket.core;

import com.eventpool.common.entities.TicketInventoryDetails;
import com.eventpool.ticket.commands.TicketSoldCommand;

public interface TicketDomainApi {
	
	
	 TicketInventoryDetails updateTicketSold(TicketSoldCommand cmd);
}

