package com.eventpool.common.dto;

import java.util.List;
import java.util.Map;

public class PackageDTO extends AuditableIdDTO{

	private String planName;
	private String curreny;
	private Double price;
	private String eventUrl;
	private Map<Integer,String> featureMap;
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getCurreny() {
		return curreny;
	}
	public void setCurreny(String curreny) {
		this.curreny = curreny;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public Map<Integer, String> getFeatureMap() {
		return featureMap;
	}
	public void setFeatureMap(Map<Integer, String> featureMap) {
		this.featureMap = featureMap;
	}
	
	
	
}
