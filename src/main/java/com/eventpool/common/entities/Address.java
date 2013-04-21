package com.eventpool.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;

@Embeddable
@MappedSuperclass
public class Address implements Serializable{


	@NotNull
	@Column(name="address1")
	private String address1;
	
	@NotNull
	@Column(name="address2")
	private String address2;
	
	@NotNull
	@Column(name = "city")
	private String city;
	
	@NotNull
	@Column(name = "state")
	private String state;
	
	@NotNull
	@Column(name = "country")
	private String country;
	
	@NotNull
	@Column(name="zipcode")
	@Min(value=100000l,message="Zipcode should be minimum 100000")
	@Max(value=999999l,message="Zipcode should be maximum 999999")
	private Long zipCode;

	@Column(name="phonenumber")
	private String phoneNumber;
	
	@NotNull
	@EmailAddressValidation
	@Column(name="email")
	@Size(max=255)
	private String email;
	
	@Column(name="fax")
	private String fax;
	
	@Column(name="mobilenumber")
	private String mobileNumber;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getZipCode() {
		return zipCode;
	}

	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	

}
