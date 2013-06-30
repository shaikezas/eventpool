package com.eventpool.web.forms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;

import com.eventpool.common.type.Gender;

public class UserForm {
	
	
	

	
	private String fname;
	
	  boolean accountEnabled =true; // This would depend upon business requirements

	  boolean accountExpired = false;
	
	private String lname;
	
	private String userName;
	
	private String password;

	
	private String email;

	private String company;

	private String phone;

	private String mobile;

	private String altEmail;

	private String homeAddress;

	private String officeAddress;

	private String shippingAddress;

	private Gender geneder;

	private Date dob;

	private String companyUrl;

	private String createdDate ;

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


	public Gender getGeneder() {
		return geneder;
	}

	public void setGeneder(Gender geneder) {
		this.geneder = geneder;
	}
	
		
	  public enum Roles implements GrantedAuthority {
		    ROLE_USER, ROLE_ADMIN;
		    public String getAuthority() {
		      return name();
		    }
		  }
	  
		public boolean isAccountEnabled() {
			return accountEnabled;
		}
		public void setAccountEnabled(boolean accountEnabled) {
			this.accountEnabled = accountEnabled;
		}
		public boolean isAccountExpired() {
			return accountExpired;
		}
		public void setAccountExpired(boolean accountExpired) {
			this.accountExpired = accountExpired;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		  
		  public String getCreatedDate() {
			return createdDate;
		}
		  
		  public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		  
}
