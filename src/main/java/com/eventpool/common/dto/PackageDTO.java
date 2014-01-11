package com.eventpool.common.dto;

import java.util.List;

public class PackageDTO extends AuditableIdDTO{

	private String planName;
	private String curreny;
	private Double price;
	private String eventUrl;
	private String features;
	private List<String> featuresList;
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
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public List<String> getFeaturesList() {
		return featuresList;
	}
	public void setFeaturesList(List<String> featuresList) {
		this.featuresList = featuresList;
	}
	
	
	
}
