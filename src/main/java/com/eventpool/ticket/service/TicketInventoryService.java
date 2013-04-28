package com.eventpool.ticket.service;

import com.eventpool.ticket.commands.ICommand;

public interface TicketInventoryService {
	
	public int getTicketInventory(Long ticketId);
	
	Boolean executeCommand(ICommand cmd) throws Exception;

}
