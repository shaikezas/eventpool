package com.eventpool.common.entities;

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


@Entity
@Table(name = "`ORDER`")
public class Order extends AuditableIdEntity {

	@NotNull
	@Column(name="BUYER_FNAME")
	private String firstName;
	
	@NotNull
	@Column(name="BUYER_LNAME")
	private String lastName;
	
	@NotNull
	@EmailAddressValidation
	@Column(name="BUYER_EMAIL")
	@Size(max=255)
	private String email;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinColumn(name="ADDRESS_ID",referencedColumnName="ID")
	private Address billingAddress;
	
	
	@Column(name="GROSS_AMOUNT")
	private Double grossAmount;
	
	@Column(name="NET_AMOUNT")
	private Double netAmount;
	
	@Column(name="DISCOUNT_AMOUNT")
	private Double discountAmount;
	
	@Column(name="DISCOUNT_COUPON")
	private String dicountCoupon;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PAYMENT_CURRENCY")
	private CurrencyType paymentCurrency;
	
	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="ORDER_ID",referencedColumnName="ID")
	private List<Suborder> suborders;

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

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
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

	public List<Suborder> getSuborders() {
		return suborders;
	}
	
	public void setSuborders(List<Suborder> suborders) {
		this.suborders = suborders;
	}

	public String getDicountCoupon() {
		return dicountCoupon;
	}

	public void setDicountCoupon(String dicountCoupon) {
		this.dicountCoupon = dicountCoupon;
	}
	
	@PostUpdate
	@PostPersist
	public void onPersist(){
		List<Suborder> listOfSuborders = this.getSuborders();
		if ( listOfSuborders!= null && listOfSuborders.size()>0){
			for ( Suborder suborder : listOfSuborders){
				suborder.setOrder(this);
			}
		}
	}


}
