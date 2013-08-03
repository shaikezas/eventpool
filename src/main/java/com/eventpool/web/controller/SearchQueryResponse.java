package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchQueryResponse {

	List<EventSearchRecord> eventSearchRecords = new ArrayList<EventSearchRecord>();
	List<FilterItem> subCategoryFilterItems = new ArrayList<FilterItem>();
	List<FilterItem> eventDateFilterItems = new ArrayList<FilterItem>();
	List<FilterItem> eventTypeFilterItems = new ArrayList<FilterItem>();
	List<FilterItem> cityIdFilterItems = new ArrayList<FilterItem>();
	
	public List<EventSearchRecord> getEventSearchRecords() {
		return eventSearchRecords;
	}
	public void setEventSearchRecords(List<EventSearchRecord> eventSearchRecords) {
		this.eventSearchRecords = eventSearchRecords;
	}
	public List<FilterItem> getSubCategoryFilterItems() {
		return subCategoryFilterItems;
	}
	public void setSubCategoryFilterItems(List<FilterItem> subCategoryFilterItems) {
		this.subCategoryFilterItems = subCategoryFilterItems;
	}
	public List<FilterItem> getEventDateFilterItems() {
		return eventDateFilterItems;
	}
	public void setEventDateFilterItems(List<FilterItem> eventDateFilterItems) {
		this.eventDateFilterItems = eventDateFilterItems;
	}
	public List<FilterItem> getEventTypeFilterItems() {
		return eventTypeFilterItems;
	}
	public void setEventTypeFilterItems(List<FilterItem> eventTypeFilterItems) {
		this.eventTypeFilterItems = eventTypeFilterItems;
	}
	public List<FilterItem> getCityIdFilterItems() {
		return cityIdFilterItems;
	}
	public void setCityIdFilterItems(List<FilterItem> cityIdFilterItems) {
		this.cityIdFilterItems = cityIdFilterItems;
	}
	
}
