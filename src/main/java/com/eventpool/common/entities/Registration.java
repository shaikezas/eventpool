package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.eventpool.common.annotation.EmailAddressValidation;


@Entity
@Table(name = "REGISTRATION")
public class Registration extends AuditableIdEntity {

	
	@NotNull
	@Column(name="NAME")
	private String name;
	
	@Column(name="COMPANY")
	private String company;
	
	@NotNull
	@EmailAddressValidation
	@Column(name="EMAIL")
	@Size(max=255)
	private String email;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="SUBORDER_ID")
	private Long suborderId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getSuborderId() {
		return suborderId;
	}

	public void setSuborderId(Long suborderId) {
		this.suborderId = suborderId;
	}
	
	
}
