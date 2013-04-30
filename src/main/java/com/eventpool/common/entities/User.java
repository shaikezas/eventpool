package com.eventpool.common.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.eventpool.common.type.GENDER;
import com.eventpool.common.type.TicketType;

@Entity
@Table(name = "USER")
public class User extends IdEntity{
	
	@Column(name = "FNAME")
	private String fname;
	
	@Column(name = "LNAME")
	private String lname;

	@NotNull
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "COMPANY")
	private String company;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "ALT_EMAIL")
	private String altEmail;

	@Column(name = "HOME_ADDRESS")
	private String homeAddress;

	@Column(name = "OFFICE_ADDRESS")
	private String officeAddress;

	@Column(name = "SHIPPING_ADDRESS")
	private String shippingAddress;

	@Column(name = "GENDER")
	private GENDER geneder;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "COMPANY_URL")
	private String companyUrl;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate = new Date();

	@Column(name = "MODIFIED_DATE", nullable = false)
	private Date modifiedDate = new Date();

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public GENDER getGeneder() {
		return geneder;
	}

	public void setGeneder(GENDER geneder) {
		this.geneder = geneder;
	}
	
		
}
