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
	List<FilterItem> countryFilterItems = new ArrayList<FilterItem>();
	FilterItem otherCountries = new FilterItem();
	long noOfresults=0;
	String query;
	
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
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public long getNoOfresults() {
		return noOfresults;
	}
	public void setNoOfresults(long noOfresults) {
		this.noOfresults = noOfresults;
	}
	public FilterItem getOtherCountries() {
		return otherCountries;
	}
	public void setOtherCountries(FilterItem otherCountries) {
		this.otherCountries = otherCountries;
	}
	public List<FilterItem> getCountryFilterItems() {
		return countryFilterItems;
	}
	public void setCountryFilterItems(List<FilterItem> countryFilterItems) {
		this.countryFilterItems = countryFilterItems;
	}
}
