package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eventpool.common.dto.EventInfoSettings;

public class PayPalDTO implements Serializable{

	String successUrl;
	String cancelUrl;
	String currency="USD";
	Long orderId;
	List<PayPalItemDTO> payPalItemDTOs;

	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public List<PayPalItemDTO> getPayPalItemDTOs() {
		if(payPalItemDTOs==null) payPalItemDTOs = new ArrayList<PayPalItemDTO>();
		return payPalItemDTOs;
	}
	public void setPayPalItemDTOs(List<PayPalItemDTO> payPalItemDTOs) {
		this.payPalItemDTOs = payPalItemDTOs;
	}
	
}
