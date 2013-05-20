package com.eventpool.common.dto;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.eventpool.common.type.Gender;

public class UserSettingJson {
	
	private Map<String,Boolean> fname;
	private Map<String,Boolean> lname;
	private Map<String,Boolean> email;
	private Map<String,Boolean> company;
	private Map<String,Boolean> phone;
	private Map<String,Boolean> mobile;
	private Map<String,Boolean> altEmail;
	private Map<String,Boolean> homeAddress;
	private Map<String,Boolean> officeAddress;
	private Map<String,Boolean> shippingAddress;
	private Map<String,Boolean> geneder;
	private Map<String,Boolean> dob;
	private Map<String,Boolean> companyUrl;
	public Map<String, Boolean> getFname() {
		return fname;
	}
	public void setFname(Map<String, Boolean> fname) {
		this.fname = fname;
	}
	public Map<String, Boolean> getLname() {
		return lname;
	}
	public void setLname(Map<String, Boolean> lname) {
		this.lname = lname;
	}
	public Map<String, Boolean> getEmail() {
		return email;
	}
	public void setEmail(Map<String, Boolean> email) {
		this.email = email;
	}
	public Map<String, Boolean> getCompany() {
		return company;
	}
	public void setCompany(Map<String, Boolean> company) {
		this.company = company;
	}
	public Map<String, Boolean> getPhone() {
		return phone;
	}
	public void setPhone(Map<String, Boolean> phone) {
		this.phone = phone;
	}
	public Map<String, Boolean> getMobile() {
		return mobile;
	}
	public void setMobile(Map<String, Boolean> mobile) {
		this.mobile = mobile;
	}
	public Map<String, Boolean> getAltEmail() {
		return altEmail;
	}
	public void setAltEmail(Map<String, Boolean> altEmail) {
		this.altEmail = altEmail;
	}
	public Map<String, Boolean> getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Map<String, Boolean> homeAddress) {
		this.homeAddress = homeAddress;
	}
	public Map<String, Boolean> getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(Map<String, Boolean> officeAddress) {
		this.officeAddress = officeAddress;
	}
	public Map<String, Boolean> getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(Map<String, Boolean> shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public Map<String, Boolean> getGeneder() {
		return geneder;
	}
	public void setGeneder(Map<String, Boolean> geneder) {
		this.geneder = geneder;
	}
	public Map<String, Boolean> getDob() {
		return dob;
	}
	public void setDob(Map<String, Boolean> dob) {
		this.dob = dob;
	}
	public Map<String, Boolean> getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(Map<String, Boolean> companyUrl) {
		this.companyUrl = companyUrl;
	}

}
