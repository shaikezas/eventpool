package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.List;

import com.eventpool.common.dto.EventInfoSettings;

public class PayPalItemDTO implements Serializable{

	String amount;
	int itemQuantity;
	String itemName;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
