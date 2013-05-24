package com.eventpool.web.forms;

import java.util.List;
import java.util.Map;

import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.type.EventInfoType;

public class EventFormSettings {

	private Long eventId;
	private Map<String,List<EventInfoSettings>> map = null;
	private EventInfoType infoType;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public Map<String, List<EventInfoSettings>> getMap() {
		return map;
	}
	
	public void setMap(Map<String, List<EventInfoSettings>> map) {
		this.map = map;
	}
	
	public EventInfoType getInfoType() {
		return infoType;
	}
	
	public void setInfoType(EventInfoType infoType) {
		this.infoType = infoType;
	}
	
	
	
}
