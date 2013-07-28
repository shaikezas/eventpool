package com.eventpool.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.eventpool.common.type.TicketType;


@Entity
@Table(name = "INVOICE")
public class Invoice extends AuditableIdEntity  {
	
	@NotNull(message = "Can't be Empty")
	@Column(name = "ORDER_ID")
	private Long orderId;
	
	@NotNull(message = "Can't be Empty")
	@Column(name = "SUBORDER_ID")
	private Long suborderId;
	
	@NotNull(message = "Can't be Empty")
	@Column(name = "EVENT_ID")
	private Long eventId;

	@Column(name = "EVENT_DATE")
	private Date eventDate;

	@Column(name = "ORGANIZER_NAME")
	private String organizerName;
	
	@Column(name = "ORGANIZER_CONTACT")
	private String organizerContact;
	
	@Column(name = "BUYER")
	private String buyer;
	
	@Column(name = "BUYER_MAIL")
	private String buyerMail;

	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name="PAY_TYPE")
	@Enumerated(EnumType.STRING)
	private TicketType ticketType;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	@Column(name = "PRICE")
	private Double price = 0.0D;
	
	@Column(name = "TOTAL_PRICE")
	private Double totalPrice = 0.0D;
	
	@Column(name = "TOTAL_AMOUNT")
	private Double totalAmount = 0.0D;
	
	@Column(name = "TERMS_CONDITIONS")
	private String termsAndConditions;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private Double discountAmount;
	
	@Column(name = "EVENT_NAME")
	private String eventName;
	
	@Column(name = "TICKET_NAME")
	private String ticketName;
	
	@Column(name = "VENUE")
	private String venue;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSuborderId() {
		return suborderId;
	}

	public void setSuborderId(Long suborderId) {
		this.suborderId = suborderId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public String getOrganizerContact() {
		return organizerContact;
	}

	public void setOrganizerContact(String organizerContact) {
		this.organizerContact = organizerContact;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}


	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getTicketName() {
		return ticketName;
	}
	
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getBuyerMail() {
		return buyerMail;
	}
	
	public void setBuyerMail(String buyerMail) {
		this.buyerMail = buyerMail;
	}
	 
}