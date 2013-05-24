package com.eventpool.event.service.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.entities.EventDefaultSettings;
import com.eventpool.common.repositories.EventDefaultSettingsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class EventDefaultSettingsService {

	
	@Resource
	private EventDefaultSettingsRepository defaultSettingsRepository;
	
	
	private List<EventInfoSettings> settings = null;
	
	public EventDefaultSettings getEventDefaultSettingsByName(String name){
		return defaultSettingsRepository.findByName(name);
	}
	
	public List<EventInfoSettings> getEventInfoSettings(){
		if(settings==null){
		EventDefaultSettings eventSettings = getEventDefaultSettingsByName("EVENTINFO");
		Gson gson = new Gson();
		Type type = new TypeToken<List<EventInfoSettings>>(){}.getType();
		settings = gson.fromJson(eventSettings.getSettings(), type);
		}
		return settings;
	}
	
	
}
