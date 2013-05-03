package com.eventpool.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.order.service.OrderService;

public class OrderServiceTest extends BaseTest{
	
	@Resource
	OrderService orderService;
	
	@Test
    public void registerTicket() throws Exception {
		List<TicketRegisterDTO> ticketRegisters = new ArrayList<TicketRegisterDTO>();
		
		TicketRegisterDTO dto = new TicketRegisterDTO();
		
		dto.setPrice(100D);
		dto.setQty(2);
		dto.setTicketId(101L);
		
TicketRegisterDTO dto1 = new TicketRegisterDTO();
		
		dto1.setPrice(100D);
		dto1.setQty(2);
		dto1.setTicketId(102L);
		
		ticketRegisters.add(dto);
//		ticketRegisters.add(dto1);
		
		
		orderService.registerOrder(ticketRegisters);
		Thread.sleep(1000*40);
	}
    

}
