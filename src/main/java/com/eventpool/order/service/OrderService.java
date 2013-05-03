package com.eventpool.order.service;

import java.util.List;

import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.web.forms.OrderRegisterForm;

public interface OrderService {

	void creteOrder(OrderDTO orderDTO) throws Exception;
	
	OrderRegisterForm registerOrder(List<TicketRegisterDTO> ticketRegisters) throws Exception;
}
