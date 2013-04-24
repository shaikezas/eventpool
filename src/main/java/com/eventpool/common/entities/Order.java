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
@Table(name = "order")
public class Order extends AuditableIdEntity {

	@NotNull
	@Column(name="buyer_first_name")
	private String firstName;
	
	@NotNull
	@Column(name="buyer_last_name")
	private String lastName;
	
	@NotNull
	@EmailAddressValidation
	@Column(name="buyer_email")
	@Size(max=255)
	private String email;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address billingAddress;
	
	
	@Column(name="gross_amount")
	private Double grossAmount;
	
	@Column(name="net_amount")
	private Double netAmount;
	
	@Column(name="discount_amount")
	private Double discountAmount;
	
	@Column(name="discount_coupon")
	private Integer dicountCoupon;
	
	@Enumerated(EnumType.STRING)
	@Column(name="payment_currency")
	private CurrencyType paymentCurrency;
	
	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="order_id",referencedColumnName="id")
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

	public Integer getDicountCoupon() {
		return dicountCoupon;
	}

	public void setDicountCoupon(Integer dicountCoupon) {
		this.dicountCoupon = dicountCoupon;
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
