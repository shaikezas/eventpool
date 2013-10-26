package com.eventpool.common.dto;


public class TicketInventoryDTO {
	
	private Long ticketId;
	private int maxQty;
	private int qty;
	private int blockingQty;
	private int soldQty;
	private double price;
	private double totalTicketSoldPrice;
	private double totalTicketsPrice;
	private String ticketName;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public int getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(int maxQty) {
		this.maxQty = maxQty;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getBlockingQty() {
		return blockingQty;
	}
	public void setBlockingQty(int blockingQty) {
		this.blockingQty = blockingQty;
	}
	public int getSoldQty() {
		return maxQty-qty;
	}
	public void setSoldQty(int soldQty) {
		this.soldQty = soldQty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalTicketSoldPrice() {
		return getSoldQty()*price;
	}
	public void setTotalTicketSoldPrice(double totalTicketSoldPrice) {
		this.totalTicketSoldPrice = totalTicketSoldPrice;
	}
	public double getTotalTicketsPrice() {
		return this.maxQty*price;
	}
	public void setTotalTicketsPrice(double totalTicketsPrice) {
		this.totalTicketsPrice = totalTicketsPrice;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	
}