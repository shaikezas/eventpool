package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.List;

import com.eventpool.common.dto.EventInfoSettings;

public class TicketBuyerDTO implements Serializable{

	public Long ticketId;
	public List<UserDTO> userDTOs;
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public List<UserDTO> getUserDTOs() {
		return userDTOs;
	}
	public void setUserDTOs(List<UserDTO> userDTOs) {
		this.userDTOs = userDTOs;
	}
	
}
