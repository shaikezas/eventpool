package com.eventpool.common.dto;



public class RegistrationDTO extends AuditableIdDTO {

	
	private String attendeeInfo;
	
	private Long suborderId;

	public String getAttendeeInfo() {
		return attendeeInfo;
	}

	public void setAttendeeInfo(String attendeeInfo) {
		this.attendeeInfo = attendeeInfo;
	}

	public Long getSuborderId() {
		return suborderId;
	}

	public void setSuborderId(Long suborderId) {
		this.suborderId = suborderId;
	}

	
	
}
