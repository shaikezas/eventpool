package com.eventpool.common;

import javax.annotation.Resource;

import org.junit.Test;

import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;
import com.eventpool.ticket.service.TicketInventoryService;

public class TicketServiceTest extends BaseTest{
	
	@Resource
	TicketInventoryService inventoryService;
	
	@Test
    public void updateTicketInventory() throws Exception {
		
		
		TicketBlockedCommand blockcmd = new TicketBlockedCommand();
		blockcmd.setBlockingQty(1);
		blockcmd.setTicketId(101L);
		
		TicketOrderedCommand cmd = new TicketOrderedCommand();
		cmd.setQty(1);
		cmd.setTicketId(101L);
		
		TicketUnBlockedCommand unblockcmd = new TicketUnBlockedCommand();
		unblockcmd.setBlockingQty(1);
		unblockcmd.setTicketId(101L);
		
		//Thread 1
		
		inventoryService.executeCommand(blockcmd);
		
		inventoryService.executeCommand(cmd);
		
		// Thread 2
		inventoryService.executeCommand(blockcmd);
		
		inventoryService.executeCommand(unblockcmd);
		
	}
    

}
