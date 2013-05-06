package com.eventpool.common;

import javax.annotation.Resource;

import org.junit.Test;

import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.repositories.TicketRegisterRepository;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.ticket.service.TicketInventoryUnblockedService;

public class TicketRegisterServiceTest extends BaseTest{
	
	@Resource
	TicketInventoryService inventoryService;
	
	@Resource
	TicketInventoryUnblockedService unBlockedService;
	
	@Resource
	TicketRegisterRepository registerRepository;
	
//	@Test
    public void registerTicket() throws Exception {
		
		TicketRegister register = registerRepository.findOne(2L);
		unBlockedService.registerTask(register, 15);
		Thread.sleep(1000*30);
	}
    

}
