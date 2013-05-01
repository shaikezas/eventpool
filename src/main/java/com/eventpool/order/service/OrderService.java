package com.eventpool.order.service;

import com.eventpool.common.dto.OrderDTO;

public interface OrderService {

	void creteOrder(OrderDTO orderDTO) throws Exception;
}
