package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;


@Entity
@Table(name = "order")
public class Order extends AuditableIdEntity {

	@NotNull
	@Column(name="buyer_first_name")
	private String firstName;
	
	@NotNull
	@Column(name="buyer_second_name")
	private String secondName;
	
	@NotNull
	@EmailAddressValidation
	@Column(name="buyer_email")
	@Size(max=255)
	private String email;
	
	@Embedded
	private Address billingAddress;
	
	@Embedded
	private CardInfo cardInfo;
	
	@Column(name="gross_amount")
	private Double grossAmount;
	
	@Column(name="net_amount")
	private Double netAmount;
	
	@Column(name="discount_amount")
	private Double discountAmount;
	
	@Column(name="discount_coupon")
	private Integer dicountCoupon;
	
	@Column(name="payment_currency")
	private String paymentCurrency;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
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

	public Integer getDicountCoupon() {
		return dicountCoupon;
	}

	public void setDicountCoupon(Integer dicountCoupon) {
		this.dicountCoupon = dicountCoupon;
	}

	public String getPaymentCurrency() {
		return paymentCurrency;
	}

	public void setPaymentCurrency(String paymentCurrency) {
		this.paymentCurrency = paymentCurrency;
	}
	
	
}
