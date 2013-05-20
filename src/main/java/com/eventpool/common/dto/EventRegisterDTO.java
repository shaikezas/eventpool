package com.eventpool.common.dto;

import java.util.List;

import com.eventpool.common.type.CurrencyType;

public class EventRegisterDTO {
	
	private Long eventId;
	private List<TicketRegisterDTO> ticketRegisterDTOs;
	private String dicountCoupon;
	private CurrencyType paymentCurrency;
	private Double discountAmount = 0.0D; 
	private Integer subCategoryId;
	private String organizerName; 

	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public List<TicketRegisterDTO> getTicketRegisterDTOs() {
		return ticketRegisterDTOs;
	}
	public void setTicketRegisterDTOs(List<TicketRegisterDTO> ticketRegisterDTOs) {
		this.ticketRegisterDTOs = ticketRegisterDTOs;
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
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
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
	
	
	

}
