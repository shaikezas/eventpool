package com.eventpool.common;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Event;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.ticket.commands.TicketSoldCommand;
import com.eventpool.ticket.service.TicketInventoryService;

public class TicketServiceTest extends BaseTest{
	
	@Resource
	TicketInventoryService inventoryService;
	
	@Test
    public void updateTicketInventory() throws Exception {
		inventoryService.executeCommand(new TicketSoldCommand());
	}
    

}
