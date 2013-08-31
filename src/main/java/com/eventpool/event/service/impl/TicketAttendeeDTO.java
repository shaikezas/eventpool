package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eventpool.common.dto.EventInfoSettings;

public class TicketAttendeeDTO implements Serializable{

	public Long ticketId;
	//public List<EventInfoSettings> eventInfoSettings;
	
	public Set<String> headers;
	public List<Map<String,String>> attendeeListMap;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public Set<String> getHeaders() {
		return headers;
	}
	public void setHeaders(Set<String> headers) {
		this.headers = headers;
	}
	public List<Map<String, String>> getAttendeeListMap() {
		return attendeeListMap;
	}
	public void setAttendeeListMap(List<Map<String, String>> attendeeListMap) {
		this.attendeeListMap = attendeeListMap;
	}
	
}
