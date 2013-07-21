package com.eventpool.event.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.dto.EventOrderSettings;
import com.eventpool.common.dto.UserEventSettingDTO;
import com.eventpool.common.entities.EventDefaultSettings;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.Registration;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.repositories.EventDefaultSettingsRepository;
import com.eventpool.common.repositories.SuborderRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class EventSettingsService {

	
	@Resource
	private EventDefaultSettingsRepository eventDefaultSettingsRepository;
	
	@Resource
	private SuborderRepository suborderRepository;
	
	private List<EventInfoSettings> settings = null;
	
	public EventDefaultSettings getEventDefaultSettingsByName(String name){
		return eventDefaultSettingsRepository.findByName(name);
	}
	
	public List<EventInfoSettings> getEventDefaultSettings(){
		if(settings==null){
		EventDefaultSettings eventSettings = getEventDefaultSettingsByName("EVENTINFO");
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<EventInfoSettings>>(){}.getType();
		settings = gson.fromJson(eventSettings.getSettings(), type);
		}
		return settings;
	}
	
	public UserEventSettingDTO getUserEventSettings(String json){
		Gson gson = new Gson();
		UserEventSettingDTO userEventSettingDTO = gson.fromJson(json, UserEventSettingDTO.class);
		return userEventSettingDTO;
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
	
	public List<EventInfoSettings> getEventInfoSettings(EventDTO event){
		String infoSettings = event.getEventSettingsDTO().getEventInfoSettings();
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<EventInfoSettings>>(){}.getType();
		return gson.fromJson(infoSettings, type);
	}
	
	public List<EventInfoSettings> getEventInfoSettings(String data){
		List<EventInfoSettings> eventSettings = null;
		Gson gson = new Gson();
		Type type = new TypeToken<LinkedList<EventInfoSettings>>(){}.getType();
		eventSettings = gson.fromJson(data, type);
		return eventSettings;
	}
	
	@Transactional(readOnly=true)
	public TicketAttendeeDTO getAttendes(Long ticketId){
		TicketAttendeeDTO ticketAttendeeDTO = new TicketAttendeeDTO();
		ticketAttendeeDTO.setTicketId(ticketId);
		List<EventInfoSettings> eventInfoSettings = new ArrayList<EventInfoSettings>();
		ticketAttendeeDTO.setEventInfoSettings(eventInfoSettings );
		List<Suborder> suborders = suborderRepository.getAttendes(ticketId);
		if(suborders!=null && suborders.size()>0){
			for(Suborder suborder:suborders){
				List<Registration> registrations = suborder.getRegistrations();
				if(registrations!=null && registrations.size()>0){
					for(Registration registration:registrations){
						String attendeeInfo = registration.getAttendeeInfo();
						if(attendeeInfo!=null){
							eventInfoSettings.addAll(getEventInfoSettings(attendeeInfo));
						}
					}
				}
			}
		}
		return ticketAttendeeDTO;
	}
	@Transactional(readOnly=true)
	public TicketBuyerDTO getBuyers(Long ticketId){
		TicketBuyerDTO ticketBuyerDTO = new TicketBuyerDTO();
		ticketBuyerDTO.setTicketId(ticketId);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		ticketBuyerDTO.setUserDTOs(userDTOs );
		List<Order> orders = suborderRepository.getBuyers(ticketId);

		if(orders!=null && orders.size()>0){
			for(Order order:orders){
				UserDTO userDTO = new UserDTO();
				userDTO.setFname(order.getFirstName());
				userDTO.setLname(order.getLastName());
				userDTO.setEmail(order.getEmail());
				userDTOs.add(userDTO);
			}
		}
		return ticketBuyerDTO;
	}
}
