package com.eventpool.ticket.core;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;

public interface TicketDomainApi {
	
	
	 TicketInventoryDetails updateTicketOrdered(TicketOrderedCommand cmd);
	 
	 TicketInventoryDetails updateTicketBlocked(TicketBlockedCommand cmd);
	 
	 TicketInventoryDetails updateTicketUnBlocked(TicketUnBlockedCommand cmd);
}

