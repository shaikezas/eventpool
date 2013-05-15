package com.eventpool.ticket.service;

import com.eventpool.common.commands.ICommand;
import com.eventpool.common.entities.TicketInventory;

public interface TicketInventoryService {
	
	public TicketInventory getTicketInventory(Long ticketId);
	
	public int getTicketMaxInventory(Long ticketId);
	
	public int getSellableTicketInventory(Long ticketId);
	
	public String getAggregateTicketInventoryByEvent(Long eventId);
	
	Object executeCommand(ICommand cmd) throws Exception;

}
