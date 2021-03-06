package com.eventpool.web.forms;

import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.eventpool.common.type.TicketType;


public class TicketForm {
	
	private Long id;
	@Size(max=128,message="please enter ticket name less than 128 characters.")
	private String name;
	
	private String description;
	String saleStart;
	
	String saleEnd;
	
	String dateDesc;
	
	private Boolean soldOut = false;
	
	private Boolean isActive=true;
	
	private Integer quantity;
	
	private boolean disableTicket = false;
	
	private Long eventId;
	
	private Double price;
	
	private Integer minQty;
	
	private Integer maxQty;
	
	private TicketType ticketType;
	
	private boolean showsettings = false;
	
	private boolean showPrice = false ;
	
	private boolean showFree = false;
	
	private int ticketOrder;	
	
	private Long createdBy;
	
	private Long selectedQty = 0L;
	
	private List<Dropdown> qtyList;
	
	private Boolean deleted=false;

	
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
		/*if(this.showFree)
			return TicketType.FREE;
		
		if(this.showPrice)
				return TicketType.PAID;
		return TicketType.PAID;*/
		
		return ticketType;
	}
	
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}	
	public boolean getShowPrice() {
		return showPrice;
	}
	
	public void setShowPrice(boolean showPrice) {
		this.showPrice = showPrice;
	}
	
	public boolean getShowFree() {
		return showFree;
	}
	
	public void setShowFree(boolean showFree) {
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
	
	public List<Dropdown> getQtyList() {
		return qtyList;
	}
	
	public void setQtyList(List<Dropdown> qtyList) {
		this.qtyList = qtyList;
	}
	
	
	public Long getSelectedQty() {
		return selectedQty;
	}
	
	public void setSelectedQty(Long selectedQty) {
		this.selectedQty = selectedQty;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Boolean getSoldOut() {
		return soldOut;
	}

	public void setSoldOut(Boolean soldOut) {
		this.soldOut = soldOut;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public boolean getDisableTicket() {
		return disableTicket;
	}

	public void setDisableTicket(boolean disableTicket) {
		this.disableTicket = disableTicket;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	
	
}
