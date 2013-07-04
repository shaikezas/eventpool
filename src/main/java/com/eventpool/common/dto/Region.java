package com.eventpool.common.dto;

public class Region {
	
	private Integer cityId;
	private Integer stateId;
	private Integer countryId;
	
	private String  cityName;
	private String stateName;
	private String countryName;
	
	private String flag;
	
	public static final String SEPARATOR = "|";

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public String getFlag() {
		return flag;
	}
	
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	@Override
	public String toString() {
		return cityName + SEPARATOR +stateName+SEPARATOR+countryName;
	}
	

}
