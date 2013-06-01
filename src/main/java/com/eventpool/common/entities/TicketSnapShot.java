package com.eventpool.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.eventpool.common.type.TicketType;

@Entity
@Table(name = "TICKET_SNAPSHOT")
public class TicketSnapShot extends AuditableIdEntity{
	
	@Column(name = "SALE_STARTDATE", nullable = false)
	Date saleStart;
	
	@Column(name = "SALE_ENDDATE", nullable = false)
	Date saleEnd;
	
	@Column(name="ACTIVE",nullable=false)
	@Type(type = "yes_no")
	private Boolean isActive;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@NotNull
	@Column(name="TICKET_ID")
	private Long ticketId;
	
	@NotNull
	@Column(name="EVENT_ID")
	private Long enventId;
	
	@Column(name="MIN_QTY")
	private Integer minQty;
	
	@Column(name="MAX_QTY")
	private Integer maxQty;
	
	@Column(name="TICKET_ORDER")
	private Integer ticketOrder;

	@Column(name="PAY_TYPE")
	@Enumerated(EnumType.STRING)
	private TicketType ticketType;

	public Date getSaleStart() {
		return saleStart;
	}

	public void setSaleStart(Date saleStart) {
		this.saleStart = saleStart;
	}

	public Date getSaleEnd() {
		return saleEnd;
	}

	public void setSaleEnd(Date saleEnd) {
		this.saleEnd = saleEnd;
	}



	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getEnventId() {
		return enventId;
	}

	public void setEnventId(Long enventId) {
		this.enventId = enventId;
	}


	public Integer getMinQty() {
		return minQty;
	}

	public void setMinQty(Integer minQty) {
		this.minQty = minQty;
	}

	public Integer getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(Integer ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}

}
