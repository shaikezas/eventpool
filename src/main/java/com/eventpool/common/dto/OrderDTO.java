package com.eventpool.common.dto;

import java.util.List;

import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;
import com.eventpool.common.type.CurrencyType;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.common.type.PaymentStatus;


public class OrderDTO extends AuditableIdDTO {

	private String firstName;
	
	private String lastName;
	
	@EmailAddressValidation
	@Size(max=255)
	private String email;
	
	private AddressDTO billingAddress;
	
	
	private Double grossAmount;
	
	private Double netAmount;
	
	private Double discountAmount;
	
	private String dicountCoupon;
	
	private CurrencyType paymentCurrency;
	
	private List<SuborderDTO> suborders;
	
	private OrderStatus status;
	
	private PaymentStatus paymentStatus;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressDTO billingAddress) {
		this.billingAddress = billingAddress;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CurrencyType getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(CurrencyType paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}

	public List<SuborderDTO> getSuborders() {
		return suborders;
	}
	
	public void setSuborders(List<SuborderDTO> suborders) {
		this.suborders = suborders;
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

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	

}
