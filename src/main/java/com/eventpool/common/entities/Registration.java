package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;


@Entity
@Table(name = "REGISTRATION")
public class Registration extends AuditableIdEntity {

	
	@Column(name="ATTENDEE_INFO")
	private String attendeeInfo;
	
	@Column(name="SUBORDER_ID")
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
