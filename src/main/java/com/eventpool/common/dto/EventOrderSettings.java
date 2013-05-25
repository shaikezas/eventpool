package com.eventpool.common.dto;

import com.eventpool.common.module.EventPoolConstants;


public class EventOrderSettings {

	private String orderTitle;
	
	private String instructions;
	
	private int registrationLimit= EventPoolConstants.REGISTRATION_TIME_LIMIT;

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getRegistrationLimit() {
		return registrationLimit;
	}
	
	public void setRegistrationLimit(int registrationLimit) {
		this.registrationLimit = registrationLimit;
	}
	
	
	

	
}


