package com.eventpool.event.service.impl;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.dto.EventOrderSettings;
import com.eventpool.common.entities.EventDefaultSettings;
import com.eventpool.common.repositories.EventDefaultSettingsRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class EventSettingsService {

	
	@Resource
	private EventDefaultSettingsRepository defaultSettingsRepository;
	
	
	private Set<EventInfoSettings> settings = null;
	
	public EventDefaultSettings getEventDefaultSettingsByName(String name){
		return defaultSettingsRepository.findByName(name);
	}
	
	public Set<EventInfoSettings> getEventDefaultSettings(){
		if(settings==null){
		EventDefaultSettings eventSettings = getEventDefaultSettingsByName("EVENTINFO");
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedHashSet<EventInfoSettings>>(){}.getType();
		settings = gson.fromJson(eventSettings.getSettings(), type);
		}
		return settings;
	}
	
	public EventOrderSettings  getEventOrderSettings(EventDTO event){
	    	if(event.getEventSettingsDTO()!=null && event.getEventSettingsDTO().getOrderFromSettings()!=null){
	    	String orderFromSettings = event.getEventSettingsDTO().getOrderFromSettings();
	    	 Gson gson = new Gson();
	    	 Type type = new TypeToken<EventOrderSettings>(){}.getType();
	    	 EventOrderSettings orderSettings = gson.fromJson(orderFromSettings, type);
	    	 
	    	return orderSettings;
	    	}
	    	return new EventOrderSettings();
	} 
	
	public Set<EventInfoSettings> getEventInfoSettings(EventDTO event){
		String infoSettings = event.getEventSettingsDTO().getEventInfoSettings();
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedHashSet<EventInfoSettings>>(){}.getType();
		return gson.fromJson(infoSettings, type);
	}
	
	
}
