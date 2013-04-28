package com.eventpool.ticket.core;

import com.eventpool.common.entities.TicketInventoryDetails;
import com.eventpool.ticket.commands.TicketOrderCommand;

public interface TicketDomainApi {
	
	
	 TicketInventoryDetails updateTicketOrder(TicketOrderCommand cmd);
}

