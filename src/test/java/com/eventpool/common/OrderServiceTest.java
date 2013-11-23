package com.eventpool.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderStatusDTO;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.type.CurrencyType;
import com.eventpool.order.service.OrderService;
import com.eventpool.web.controller.OrderController;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;

public class OrderServiceTest extends BaseTest{
	
	@Resource
	OrderService orderService;
	
	@Resource
	OrderController controller;
	
//	@Test
    public void registerTicket() throws Exception {
    	
    	EventRegisterDTO eventDTO = new EventRegisterDTO();
    	eventDTO.setEventId(1L);
    	eventDTO.setOrganizerName("orgname");
    	eventDTO.setPaymentCurrency(CurrencyType.USD);
    	eventDTO.setSubCategoryId(1);
    	
		List<TicketRegisterDTO> ticketRegisters = new ArrayList<TicketRegisterDTO>();
		
		TicketRegisterDTO dto = new TicketRegisterDTO();
		
		dto.setPrice(2.3D);
		dto.setQty(2);
		dto.setTicketId(1L);
		
		ticketRegisters.add(dto);
		
		eventDTO.setTicketRegisterDTOs(ticketRegisters);
		
		OrderRegisterForm form = orderService.registerOrder(eventDTO);
		
		form.setEmail("shaikezas@gmail.com");
		form.setFirstName("Ezas");
		form.setLastName("Shaik");
		
		AddressDTO address = new AddressDTO();
		address.setAddress1("BTM");
		address.setCityId(42498);
		address.setZipCode("560036");
		address.setAddress2("Thaverekere");
		
		form.setBillingAddress(address);
		
		
//		OrderStatusDTO order = controller.createOrder(form);
//		System.out.println("Status "+order.getStatus());
//		System.out.println("OrderId "+order.getOrderId());
		
		Thread.sleep(1000*40);
	}
    
	@Test
    public void placeOrder() throws Exception {
    	
    	EventRegisterDTO eventDTO = new EventRegisterDTO();
    	eventDTO.setEventId(2L);
    	eventDTO.setOrganizerName("orgname");
    	eventDTO.setPaymentCurrency(CurrencyType.USD);
    	eventDTO.setSubCategoryId(1);
    	
		List<TicketRegisterDTO> ticketRegisters = new ArrayList<TicketRegisterDTO>();
		
		TicketRegisterDTO dto = new TicketRegisterDTO();
		
		dto.setPrice(2.3D);
		dto.setQty(1);
		dto.setTicketId(2L);
		
		
		TicketRegisterDTO dto1 = new TicketRegisterDTO();
		
		dto1.setPrice(3.3D);
		dto1.setQty(1);
		dto1.setTicketId(3L);
		ticketRegisters.add(dto);
		ticketRegisters.add(dto1);
		
		eventDTO.setTicketRegisterDTOs(ticketRegisters);
		
		OrderRegisterForm form = orderService.registerOrder(eventDTO);
		
		form.setEmail("shaikezas@gmail.com");
		form.setFirstName("Ezas");
		form.setLastName("Shaik");
		
		AddressDTO address = new AddressDTO();
		address.setAddress1("BTM");
		address.setCityId(42498);
		address.setZipCode("560036");
		address.setAddress2("Thaverekere");
		
		form.setBillingAddress(address);
		int count=1;
		for(TicketRegisterForm regForm : form.getTicketRegForms()){
			System.out.println("Ticket Id:"+regForm.getTicketId()+" and Name :"+regForm.getTicketName());
		/*	regForm.setCompany("company"+count);
			regForm.setEmail("shaikezas"+count+"@gmail.com");
			regForm.setName("Ezas"+count);
			regForm.setPhone("Phone"+count);
		*/	count++;
		}
		/*OrderStatusDTO order = controller.createOrder(form);
		System.out.println("Status "+order.getStatus());
		System.out.println("OrderId "+order.getOrderId());*/
		ResponseMessage order = controller.createOrder(form);
		//System.out.println("Status "+order.getStatus());
		//System.out.println("OrderId "+order.getOrderId());
		
		Thread.sleep(1000*80);
	}
    

}
