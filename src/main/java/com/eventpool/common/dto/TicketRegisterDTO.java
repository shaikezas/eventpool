package com.eventpool.common.dto;

public class TicketRegisterDTO {

	private Long ticketId;
	private int qty;
	private Double price;
	private Long id;
	private String ticketName;
	private int registrationLimit;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public int getRegistrationLimit() {
		return registrationLimit;
	}
	public void setRegistrationLimit(int registrationLimit) {
		this.registrationLimit = registrationLimit;
	}
	
	
	
}
