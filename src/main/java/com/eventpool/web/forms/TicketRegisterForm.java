package com.eventpool.web.forms;


public class TicketRegisterForm {
	
	
	private Long ticketId;
	
	private String ticketName;
	
	private String name;
	
	private String company;
	
	private String email;
	
	private String phone;
	
	private boolean isNameRequired=true;
	
	private boolean isCompanyRequired=true;
	
	private boolean isEmailRequired=true;
	
	private boolean isPhoneRequired=true;

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

	public boolean isNameRequired() {
		return isNameRequired;
	}

	public void setNameRequired(boolean isNameRequired) {
		this.isNameRequired = isNameRequired;
	}

	public boolean isCompanyRequired() {
		return isCompanyRequired;
	}

	public void setCompanyRequired(boolean isCompanyRequired) {
		this.isCompanyRequired = isCompanyRequired;
	}

	public boolean isEmailRequired() {
		return isEmailRequired;
	}

	public void setEmailRequired(boolean isEmailRequired) {
		this.isEmailRequired = isEmailRequired;
	}

	public boolean isPhoneRequired() {
		return isPhoneRequired;
	}

	public void setPhoneRequired(boolean isPhoneRequired) {
		this.isPhoneRequired = isPhoneRequired;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	
	
	
	
}
