package com.eventpool.order.service;


public interface PaymentService {

	public String initPayment(String amount,int itemQuantity,String successUrl,String cancelUrl);
}
