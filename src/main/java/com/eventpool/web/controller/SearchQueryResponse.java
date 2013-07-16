package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchQueryResponse {

	List<EventSearchRecord> eventSearchRecords = new ArrayList<EventSearchRecord>();
	Map<String,Long> subCategoryIdMap = new HashMap<String, Long>();
	Map<String,Long> eventDateMap = new HashMap<String, Long>();
	Map<String,Long> eventTypeMap = new HashMap<String, Long>();
	Map<String,Long> cityIdMap = new HashMap<String, Long>();
	
	public List<EventSearchRecord> getEventSearchRecords() {
		return eventSearchRecords;
	}
	public void setEventSearchRecords(List<EventSearchRecord> eventSearchRecords) {
		this.eventSearchRecords = eventSearchRecords;
	}
	public Map<String, Long> getSubCategoryIdMap() {
		return subCategoryIdMap;
	}
	public void setSubCategoryIdMap(Map<String, Long> subCategoryIdMap) {
		this.subCategoryIdMap = subCategoryIdMap;
	}
	public Map<String, Long> getEventDateMap() {
		return eventDateMap;
	}
	public void setEventDateMap(Map<String, Long> eventDateMap) {
		this.eventDateMap = eventDateMap;
	}
	public Map<String, Long> getEventTypeMap() {
		return eventTypeMap;
	}
	public void setEventTypeMap(Map<String, Long> eventTypeMap) {
		this.eventTypeMap = eventTypeMap;
	}
	public Map<String, Long> getCityIdMap() {
		return cityIdMap;
	}
	public void setCityIdMap(Map<String, Long> cityIdMap) {
		this.cityIdMap = cityIdMap;
	}
	
}
