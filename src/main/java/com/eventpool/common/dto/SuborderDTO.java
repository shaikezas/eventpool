package com.eventpool.common.dto;

import java.util.List;

import com.eventpool.common.type.OrderStatus;

public class SuborderDTO extends AuditableIdDTO {

	private TicketDTO ticket;
	
    private OrderDTO order;

	private Integer subCategoryId;
	
	private Double ticketPrice;

	private Double grossAmount;
	
	private String ticketName;
	
	private Double netAmount;
	
	private Double discountAmount;
	
	private String dicountCoupon;
	
	private OrderStatus status;
	
	private String organizerName;
	
	private Long ticketRegisterId;
	
	private List<RegistrationDTO> registrations;

	public TicketDTO getTicket() {
		return ticket;
	}

	public void setTicket(TicketDTO ticket) {
		this.ticket = ticket;
	}


	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public List<RegistrationDTO> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<RegistrationDTO> registrations) {
		this.registrations = registrations;
	}

	public Long getTicketRegisterId() {
		return ticketRegisterId;
	}

	public void setTicketRegisterId(Long ticketRegisterId) {
		this.ticketRegisterId = ticketRegisterId;
	}

	public String getTicketName() {
		return ticketName;
	}
	
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
}
