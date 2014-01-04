package com.eventpool.order.service;

import com.eventpool.event.service.impl.PayPalDTO;


public interface PaymentService {

	public String initPayment(PayPalDTO payPalDTO);
}
