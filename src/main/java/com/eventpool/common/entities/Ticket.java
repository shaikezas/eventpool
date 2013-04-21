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
@Table(name = "Ticket")
public class Ticket extends AuditableIdEntity{
	
	@NotNull(message = "Can't be Empty")
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "sale_start", nullable = false)
	Date saleStart;
	
	@Column(name = "sale_end", nullable = false)
	Date saleEnd;
	
	@Column(name = "type")
    private String type;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name="event_id")
	private Long enventId;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="min_quantity")
	private Integer minQty;
	
	@Column(name="max_quantity")
	private Integer maxQty;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	
}
