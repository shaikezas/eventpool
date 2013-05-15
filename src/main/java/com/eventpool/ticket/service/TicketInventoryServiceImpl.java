package com.eventpool.ticket.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eventpool.common.commands.ICommand;
import com.eventpool.common.entities.TicketInventory;
import com.eventpool.common.exceptions.NoTicketInventoryAvailableException;
import com.eventpool.common.repositories.TicketInventoryRepository;
import com.eventpool.ticket.command.Gate;

@Service
public class TicketInventoryServiceImpl implements TicketInventoryService{
	
	@Resource
	private Gate gate;
	
	@Resource
	TicketInventoryRepository ticketInventoryRepository;
	
	public int getTicketMaxInventory(Long ticketId) {
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		
		if (ticketInventory==null){
			throw new NoTicketInventoryAvailableException("No Ticket Inventory found");
		}
		
		return ticketInventory.getMaxQty();
	}
	
	public int getSellableTicketInventory(Long ticketId) {
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		
		if (ticketInventory==null){
			throw new NoTicketInventoryAvailableException("No Ticket Inventory found");
		}
		
		return ticketInventory.getQty() - ticketInventory.getBlockingQty();
	}
	
	public TicketInventory getTicketInventory(Long ticketId) {
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		
		if (ticketInventory==null){
			throw new NoTicketInventoryAvailableException("No Ticket Inventory found");
		}
		
		return ticketInventory;
	}

	public Object executeCommand(ICommand cmd) throws Exception {
		return  gate.dispatch(cmd);
	}

}
