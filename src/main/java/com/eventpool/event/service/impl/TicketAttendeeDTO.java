package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.List;

import com.eventpool.common.dto.EventInfoSettings;

public class TicketAttendeeDTO implements Serializable{

	public Long ticketId;
	public List<EventInfoSettings> eventInfoSettings;
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public List<EventInfoSettings> getEventInfoSettings() {
		return eventInfoSettings;
	}
	public void setEventInfoSettings(List<EventInfoSettings> eventInfoSettings) {
		this.eventInfoSettings = eventInfoSettings;
	}
	
}
