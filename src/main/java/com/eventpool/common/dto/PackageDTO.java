package com.eventpool.common.dto;

import java.util.List;
import java.util.Map;

public class PackageDTO extends AuditableIdDTO{

	private String planName;
	private String currency;
	private Double price;
	private String eventUrl;
	private Map<Integer,Boolean> featureMap;
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public Map<Integer, Boolean> getFeatureMap() {
		return featureMap;
	}
	public void setFeatureMap(Map<Integer, Boolean> featureMap) {
		this.featureMap = featureMap;
	}
	
	
	
}
