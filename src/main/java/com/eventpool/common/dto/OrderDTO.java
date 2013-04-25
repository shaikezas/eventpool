package com.eventpool.common.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;
import com.eventpool.common.type.CurrencyType;


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
	

}
