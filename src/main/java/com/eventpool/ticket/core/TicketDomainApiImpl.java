package com.eventpool.ticket.core;

import org.springframework.stereotype.Service;

import com.eventpool.common.entities.TicketInventoryDetails;
import com.eventpool.ticket.commands.TicketSoldCommand;

@Service
public class TicketDomainApiImpl implements TicketDomainApi{

	public TicketInventoryDetails updateTicketSold(TicketSoldCommand cmd) {
		System.out.println("Updating Ticket Inventory...");
		return null;
	}

}
