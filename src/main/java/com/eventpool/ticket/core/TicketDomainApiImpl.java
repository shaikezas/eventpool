package com.eventpool.ticket.core;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.entities.TicketInventory;
import com.eventpool.common.repositories.TicketInventoryRepository;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketCreatedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;
import com.eventpool.ticket.commands.TicketUpdatedCommand;

@Service
public class TicketDomainApiImpl implements TicketDomainApi{
	static final Logger logger = LoggerFactory.getLogger(TicketDomainApiImpl.class);
	
	@Resource
	TicketInventoryRepository ticketInventoryRepository;
	public TicketInventoryDetails updateTicketOrdered(TicketOrderedCommand cmd) {
		logger.info("Updating Ticket Inventory...");
		Long ticketId = cmd.getTicketId();
		Integer qty = cmd.getQty();
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		TicketInventoryDetails ticketInventoryDetails = ticketInventory.decrementTicketQuantity(qty);
		ticketInventoryRepository.save(ticketInventory);
		logger.info("Updated Ticket Inventory..."+ReflectionToStringBuilder.toString(ticketInventoryDetails));
		return ticketInventoryDetails;
	}
	public TicketInventoryDetails updateTicketBlocked(TicketBlockedCommand cmd) {
		logger.info("Blocking Ticket Inventory...");
		Long ticketId = cmd.getTicketId();
		Integer blockingQty = cmd.getBlockingQty();
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		TicketInventoryDetails ticketInventoryDetails =  null;
		if(ticketInventory!=null){
			ticketInventoryDetails = ticketInventory.blockingTicketQuantity(blockingQty);
		ticketInventoryRepository.save(ticketInventory);
		}
		logger.info("Blocked Ticket Inventory..."+ReflectionToStringBuilder.toString(ticketInventoryDetails));
		return ticketInventoryDetails;
	}
	public TicketInventoryDetails updateTicketUnBlocked(
			TicketUnBlockedCommand cmd) {
		logger.info("Un Blocking Ticket Inventory...");
		Long ticketId = cmd.getTicketId();
		Integer blockingQty = cmd.getUnBlockingQty();
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		TicketInventoryDetails ticketInventoryDetails = ticketInventory.unBlockingTicketQuantity(blockingQty);
		ticketInventoryRepository.save(ticketInventory);
		logger.info("UnBlocked Ticket Inventory..."+ReflectionToStringBuilder.toString(ticketInventoryDetails));
		return ticketInventoryDetails;
	}
	public TicketInventoryDetails ticketCreated(TicketCreatedCommand cmd) {
		logger.info("Creating Ticket Inventory...");
		Long ticketId = cmd.getTicketId();
		Integer maxQty = cmd.getMaxQty();
		TicketInventoryDetails ticketInventoryDetails = createTicketInventory(ticketId, maxQty);
		logger.info("Created Ticket Inventory..."+ReflectionToStringBuilder.toString(ticketInventoryDetails));
		return ticketInventoryDetails;
	}
	private TicketInventoryDetails createTicketInventory(Long ticketId,
			Integer maxQty) {
		TicketInventory ticketInventory = new TicketInventory();
		ticketInventory.setMaxQty(maxQty);
		ticketInventory.setQty(maxQty);
		ticketInventory.setTicketId(ticketId);
		ticketInventoryRepository.save(ticketInventory);
		TicketInventoryDetails ticketInventoryDetails = new TicketInventoryDetails();
		ticketInventoryDetails.setMaxQty(maxQty);
		ticketInventoryDetails.setSellableQty(maxQty);
		ticketInventoryDetails.setTicketId(ticketId);
		ticketInventoryDetails.setMaxQtyUpdated(true);
		return ticketInventoryDetails;
	}
	public TicketInventoryDetails ticketUpdated(TicketUpdatedCommand cmd) {
		logger.info("Updating Ticket Inventory...");
		Long ticketId = cmd.getTicketId();
		Integer maxQty = cmd.getMaxQty();
		TicketInventory ticketInventory = ticketInventoryRepository.findOne(ticketId);
		TicketInventoryDetails ticketInventoryDetails = null;
		if(ticketInventory == null){
			ticketInventoryDetails = createTicketInventory(ticketId, maxQty);
		}else{
			ticketInventoryDetails =  ticketInventory.updateTicketQuantity(maxQty);
			ticketInventoryRepository.save(ticketInventory);
		}
		logger.info("Updated Ticket Inventory..."+ReflectionToStringBuilder.toString(ticketInventoryDetails));
		return ticketInventoryDetails;
	}

}
