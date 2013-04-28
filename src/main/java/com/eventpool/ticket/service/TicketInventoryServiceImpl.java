package com.eventpool.ticket.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eventpool.ticket.command.Gate;
import com.eventpool.ticket.commands.ICommand;

@Service
public class TicketInventoryServiceImpl implements TicketInventoryService{
	
	@Resource
	private Gate gate;

	public int getTicketInventory(Long ticketId) {
		return 0;
	}

	public Boolean executeCommand(ICommand cmd) throws Exception {
		return (Boolean) gate.dispatch(cmd);
	}

}
