package com.eventpool.order.service;

import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.web.forms.OrderRegisterForm;

public interface PaymentService {

	public String initPayment(OrderDTO orderDTO);
}
