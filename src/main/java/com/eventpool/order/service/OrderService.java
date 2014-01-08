package com.eventpool.order.service;

import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.web.forms.OrderRegisterForm;

public interface OrderService {

	Order createOrder(OrderDTO orderDTO) throws Exception;
	OrderRegisterForm registerOrder(EventRegisterDTO eventRegister) throws Exception;
	public void updateToken(Long orderId,String token);
	public OrderDTO getOrderDTO(Long orderId);
	public OrderDTO postOrder(Long orderId,String token,String payerId) throws Exception;
	public void releaseInventory(Long orderId);
}
