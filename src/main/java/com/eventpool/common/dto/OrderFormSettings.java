package com.eventpool.common.dto;

import java.util.Map;


public class OrderFormSettings {

	private String attendType;
	private Map<String,UserSettingJson> userSettings;

	public String getAttendType() {
		return attendType;
	}
	public void setAttendType(String attendType) {
		this.attendType = attendType;
	}
	public Map<String, UserSettingJson> getUserSettings() {
		return userSettings;
	}
	public void setUserSettings(Map<String, UserSettingJson> userSettings) {
		this.userSettings = userSettings;
	}
}
