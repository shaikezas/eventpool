package com.eventpool.order.service;

import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.web.forms.OrderRegisterForm;

public interface OrderService {

	Order createOrder(OrderDTO orderDTO) throws Exception;
	OrderRegisterForm registerOrder(EventRegisterDTO eventRegister) throws Exception;
}
