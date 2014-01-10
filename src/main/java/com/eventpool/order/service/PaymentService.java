package com.eventpool.order.service;

import urn.ebay.apis.eBLBaseComponents.AckCodeType;

import com.eventpool.event.service.impl.PayPalDTO;


public interface PaymentService {

	public String initPayment(PayPalDTO payPalDTO);

	AckCodeType getPaymentDetails(String token);
}
