package com.eventpool.common.dto;

public class OrderStatusDTO {
	
	String status="FAILED";
	Long orderId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	

}
