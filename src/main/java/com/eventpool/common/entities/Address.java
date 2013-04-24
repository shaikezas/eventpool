package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ADDRESS")
public class Address extends IdEntity{


	@NotNull
	@Column(name="ADDR_LINE1")
	private String address1;
	
	@NotNull
	@Column(name="ADDR_LINE2")
	private String address2;
	
	@NotNull
	@Column(name = "CITYID")
	private Long cityId;
	
	@Column(name="MAP_URL")
	private String mapUrl;
	
	@NotNull
	@Column(name="ZIP")
	@Min(value=100000l,message="Zipcode should be minimum 100000")
	@Max(value=999999l,message="Zipcode should be maximum 999999")
	private Long zipCode;

	@Column(name="phonenumber")
	private String phoneNumber;
	
	@Column(name="fax")
	private String fax;
	
	@Column(name="mobilenumber")
	private String mobileNumber;

	@Column(name="STATE")
	private String state;

	@Column(name="COUNTRYID")
	private String country;

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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
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
	

	
	
}
