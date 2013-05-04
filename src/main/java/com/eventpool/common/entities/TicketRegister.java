package com.eventpool.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TICKET_REGISTER")
public class TicketRegister extends IdEntity{
	
	@NotNull
	@Column(name = "TICKET_ID")
	private Long ticketId;
	
	@NotNull
	@Column(name = "TICKET_QTY")
	private int qty;
	
	@NotNull
	@Column(name = "TICKET_PRICE")
	private Double price;
	
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate = new Date();
	
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}