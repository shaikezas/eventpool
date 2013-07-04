package com.eventpool.web.forms;

public class SubOrderForm {
	
	private String evnetName;
	private String ticketName;
	private Integer quantity;
	private Long eventId;
	private Double ticketPrice;
	private String startDate;
	private String endDate;
	private String bookedOn;
	public String getBookedOn() {
		return bookedOn;
	}
	public void setBookedOn(String bookedOn) {
		this.bookedOn = bookedOn;
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
	public String getEvnetName() {
		return evnetName;
	}
	public void setEvnetName(String evnetName) {
		this.evnetName = evnetName;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

}
