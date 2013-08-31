package com.eventpool.event.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eventpool.common.dto.EventInfoSettings;

public class TicketAttendeeDTO implements Serializable{

	public Long ticketId;
	
	public Set<String> headers;
	public List<Map<String,String>> attendeeListMap;
	public List<List<String>> valueList;
	
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
	public List<List<String>> getValueList() {
		return valueList;
	}
	public void setValueList(List<List<String>> valueList) {
		this.valueList = valueList;
	}
	
}
