package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.List;

import com.eventpool.common.dto.EventInfoSettings;

public class UserDTO implements Serializable{

	String fname;
	String lname;
	String email;
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
	
}
