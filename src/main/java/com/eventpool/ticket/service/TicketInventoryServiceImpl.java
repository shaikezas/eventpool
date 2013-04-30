package com.eventpool.ticket.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eventpool.common.entities.TicketInventory;
import com.eventpool.common.exceptions.NoTicketInventoryAvailableException;
import com.eventpool.common.repositories.TicketInventoryRepository;
import com.eventpool.ticket.command.Gate;
import com.eventpool.ticket.commands.ICommand;

@Service
public class TicketInventoryServiceImpl implements TicketInventoryService{
	
	@Resource
	private Gate gate;
	
	@Resource
	TicketInventoryRepository ticketInventoryRepository;
	
	public int getTicketInventory(Long ticketId) {
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		
		if (ticketInventory==null){
			throw new NoTicketInventoryAvailableException("No Ticket Inventory found");
		}
		
		return ticketInventory.getQty();
	}
	
	public int getSellableTicketInventory(Long ticketId) {
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		
		if (ticketInventory==null){
			throw new NoTicketInventoryAvailableException("No Ticket Inventory found");
		}
		
		return ticketInventory.getQty() - ticketInventory.getBlockingQty();
	}

	public Object executeCommand(ICommand cmd) throws Exception {
		return  gate.dispatch(cmd);
	}

}
