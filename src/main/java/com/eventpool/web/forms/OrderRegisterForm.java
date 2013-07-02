package com.eventpool.web.forms;

import java.util.List;

import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;
import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.type.CurrencyType;

public class OrderRegisterForm {
	private String firstName;

	private String lastName;
	
	private int registrationLimit;

	@EmailAddressValidation
	@Size(max = 255)
	private String email;

	private AddressDTO billingAddress;

	private Double grossAmount;

	private Double netAmount;

	private Double discountAmount;

	private String dicountCoupon;

	private CurrencyType paymentCurrency;

	private Integer subCategoryId;

	private String organizerName;
	
	private int totalTickets;

	private List<TicketRegisterDTO> ticketRegisters;

	private List<TicketRegisterForm> ticketRegForms;

	private String venueName;
	private String venueAddress;
	private String startDate;
	private String endDate;

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

	public Double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Double getDiscountAmount() {
		if (discountAmount == null)
			return 0.0D;
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getDicountCoupon() {
		return dicountCoupon;
	}

	public void setDicountCoupon(String dicountCoupon) {
		this.dicountCoupon = dicountCoupon;
	}

	public CurrencyType getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(CurrencyType paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public int getTotalTickets() {
		return totalTickets;
	}
	
	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}
	
	
	public int getRegistrationLimit() {
		return registrationLimit;
	}
	
	public void setRegistrationLimit(int registrationLimit) {
		this.registrationLimit = registrationLimit;
	}

}
