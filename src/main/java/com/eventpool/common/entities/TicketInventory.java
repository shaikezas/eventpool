package com.eventpool.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.exceptions.NoTicketInventoryAvailableException;


@Entity
@Table(name = "TICKET_INVENTORY")
public class TicketInventory implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="TICKET_ID")
	private Long ticketId;
	
	@NotNull
	@Column(name = "TICKET_QTY", columnDefinition = "Integer(11) default 0")
	private Integer qty = 0;
	
	@NotNull
	@Column(name = "BLOCKED_QTY", columnDefinition = "Integer(11) default 0")
	private Integer blockingQty = 0;
	
	@Version
	@Column(name = "VERSION", columnDefinition = "Integer(11) default 0", nullable = false)
	private Integer version = 0;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	public Integer getBlockingQty() {
		return blockingQty;
	}
	
	public void setBlockingQty(Integer blockingQty) {
		this.blockingQty = blockingQty;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public TicketInventoryDetails BlockingTicketQuantity(Integer blockingQty)
			throws NoTicketInventoryAvailableException {
		TicketInventoryDetails inventoryDetails = new TicketInventoryDetails();
		if(getSellableQty(blockingQty) >= 0){
			this.blockingQty += blockingQty;
			inventoryDetails.setInvBlocked(Boolean.TRUE);
			}
			inventoryDetails.setInvQty(getSellableQty(blockingQty));
			inventoryDetails.setBlockingQty(blockingQty);
			inventoryDetails.setInvUpdated(Boolean.FALSE);
			inventoryDetails.setInvBlocked(Boolean.FALSE);
			inventoryDetails.setTicketId(this.ticketId);
		return inventoryDetails;
	}
	
	public TicketInventoryDetails decrementTicketQuantity(Integer qty)
			throws NoTicketInventoryAvailableException {
		TicketInventoryDetails inventoryDetails = new TicketInventoryDetails();
		if(this.blockingQty >= qty){
				this.qty -= qty;
				this.blockingQty -= qty;
				inventoryDetails.setInvUpdated(Boolean.TRUE);
		}
		inventoryDetails.setInvQty(getSellableQty(qty));
		inventoryDetails.setInvUpdated(Boolean.FALSE);
		inventoryDetails.setTicketId(this.ticketId);
		return inventoryDetails;
	}
	
	private int getSellableQty(Integer qty){
		return (this.qty-this.blockingQty)  - qty;
	}
	public boolean incrementTicketQuantity(Integer qty)
			 {
		this.qty += qty;
		return true;
	}
	
	public TicketInventoryDetails UnBlockingTicketQuantity(Integer blockingQty)
			throws NoTicketInventoryAvailableException {
		TicketInventoryDetails inventoryDetails = new TicketInventoryDetails();
			this.blockingQty -= blockingQty;
			inventoryDetails.setInvBlocked(Boolean.TRUE);
			inventoryDetails.setInvQty(getSellableQty(blockingQty));
			inventoryDetails.setBlockingQty(blockingQty);
			inventoryDetails.setInvUpdated(Boolean.FALSE);
			inventoryDetails.setTicketId(this.ticketId);
		return inventoryDetails;
	}
	
}
