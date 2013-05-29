package com.eventpool.web.forms;

import java.util.LinkedHashSet;
import java.util.Map;

import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.dto.EventOrderSettings;
import com.eventpool.common.type.EventInfoType;

public class EventFormSettings {

	private Long eventId;
	private Map<String,LinkedHashSet<EventInfoSettings>> map = null;
	private EventInfoType infoType;
	private EventOrderSettings orderSettings;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public Map<String, LinkedHashSet<EventInfoSettings>> getMap() {
		return map;
	}
	
	public void setMap(Map<String, LinkedHashSet<EventInfoSettings>> map) {
		this.map = map;
	}
	
	public EventInfoType getInfoType() {
		return infoType;
	}
	
	public void setInfoType(EventInfoType infoType) {
		this.infoType = infoType;
	}
	
	public EventOrderSettings getOrderSettings() {
		return orderSettings;
	}
	
	public void setOrderSettings(EventOrderSettings orderSettings) {
		this.orderSettings = orderSettings;
	}
	
	
}
