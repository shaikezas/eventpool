package com.eventpool.web.forms;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.eventpool.common.type.TicketType;


public class TicketForm {
	
	private Long id;
	private String name;
	
	private String description;
	
	String saleStart;
	
	String saleEnd;
	
	private Boolean isActive;
	
	private Integer quantity;
	
	private Long enventId;
	
	private Double price;
	
	private Integer minQty;
	
	private Integer maxQty;
	
	private TicketType ticketType;
	
	private Boolean showsettings;
	
	private Boolean showPrice ;
	
	private Boolean showFree;
	
	private int ticketOrder;	
	
	private Long createdBy;
	
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



	public String getSaleStart() {
		return saleStart;
	}

	public void setSaleStart(String saleStart) {
		this.saleStart = saleStart;
	}

	public String getSaleEnd() {
		return saleEnd;
	}

	public void setSaleEnd(String saleEnd) {
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public Boolean getShowsettings() {
		return showsettings;
	}
	
	public void setShowsettings(Boolean showsettings) {
		this.showsettings = showsettings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public TicketType getTicketType() {
		if(this.showFree)
			return TicketType.FREE;
		
		if(this.showPrice)
				return TicketType.PAID;
		return TicketType.FREE;
	}
	
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}	
	public Boolean getShowPrice() {
		return showPrice;
	}
	
	public void setShowPrice(Boolean showPrice) {
		this.showPrice = showPrice;
	}
	
	public Boolean getShowFree() {
		return showFree;
	}
	
	public void setShowFree(Boolean showFree) {
		this.showFree = showFree;
	}
	
	
	public int getTicketOrder() {
		return ticketOrder;
	}
	
	public void setTicketOrder(int ticketOrder) {
		this.ticketOrder = ticketOrder;
	}
	
	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
}
