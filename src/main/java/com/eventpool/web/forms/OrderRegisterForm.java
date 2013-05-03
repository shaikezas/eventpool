package com.eventpool.web.forms;

import java.util.List;

import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;
import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.TicketRegisterDTO;

public class OrderRegisterForm {
private String firstName;
	
	private String lastName;
	
	@EmailAddressValidation
	@Size(max=255)
	private String email;
	
	private AddressDTO billingAddress;
	
	private List<TicketRegisterDTO> ticketRegisters ;
	
	private List<TicketRegisterForm> ticketRegForms;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public List<TicketRegisterForm> getTicketRegForms() {
		return ticketRegForms;
	}

	public void setTicketRegForms(List<TicketRegisterForm> ticketRegForms) {
		this.ticketRegForms = ticketRegForms;
	}

	public List<TicketRegisterDTO> getTicketRegisters() {
		return ticketRegisters;
	}

	public void setTicketRegisters(List<TicketRegisterDTO> ticketRegisters) {
		this.ticketRegisters = ticketRegisters;
	}


	
	
	
	
	
	
}
