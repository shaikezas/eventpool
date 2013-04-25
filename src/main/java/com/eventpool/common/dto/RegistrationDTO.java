package com.eventpool.common.dto;

import com.eventpool.common.annotation.EmailAddressValidation;


public class RegistrationDTO extends AuditableIdDTO {

	
	private String name;
	
	private String company;
	
	@EmailAddressValidation
	private String email;
	
	private String phone;
	
	private Long suborderId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getSuborderId() {
		return suborderId;
	}

	public void setSuborderId(Long suborderId) {
		this.suborderId = suborderId;
	}
	
	
}
