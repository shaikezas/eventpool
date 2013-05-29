package com.eventpool.web.forms;

import java.util.Set;

import com.eventpool.common.dto.EventInfoSettings;


public class TicketRegisterForm {
	
	
	private Long ticketId;
	
	private String ticketName;
	
	private Set<EventInfoSettings> infoSettings;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public Set<EventInfoSettings> getInfoSettings() {
		return infoSettings;
	}

	public void setInfoSettings(Set<EventInfoSettings> infoSettings) {
		this.infoSettings = infoSettings;
	}
	
	
	
	
}
