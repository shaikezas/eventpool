package com.eventpool.ticket.service;

import com.eventpool.ticket.commands.ICommand;

public interface TicketInventoryService {
	
	public int getTicketInventory(Long ticketId);
	
	public int getSellableTicketInventory(Long ticketId);
	
	Object executeCommand(ICommand cmd) throws Exception;

}
