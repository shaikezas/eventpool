package com.eventpool.event.module;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.EventSettings;
import com.eventpool.common.entities.Media;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.TicketSnapShot;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.repositories.EventMediaRepository;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.repositories.EventSettingsRepository;
import com.eventpool.common.repositories.SuborderRepository;
import com.eventpool.common.type.EventStatus;


@Component
public class EventApiImpl implements EventApi{

    private static final Logger logger = LoggerFactory.getLogger(EventApiImpl.class);
	
    @Resource
    private EventpoolMapper eventpoolMapper;
    
    @Resource
    private EventRepository eventRepository;

    @Resource
    private SuborderRepository suborderRepository;
   
    @Resource
    private EventMediaRepository eventMediaRepository;
    
    @Resource
    private EventSettingsRepository eventSettingsRepository;
    
    @Transactional(rollbackFor=RuntimeException.class)
    public EventDTO saveEventDTO(EventDTO eventDTO){
    	if(eventDTO==null) throw new IllegalArgumentException("Input event DTO is null");
    	Long id = eventDTO.getId();
    	Event event = null;
    	if(id == null){
    		event = new Event();
    	}else{
    		event = eventRepository.findOne(id);
    	}
    	eventpoolMapper.mapEvent(eventDTO, event);
    	if(event.getSubCategoryId()  == null){
    		event.setSubCategoryId(1000);
    	}
    	setDefaultValues(event);
    	eventRepository.save(event);
    	eventpoolMapper.mapEventDTO(event, eventDTO);
    	logger.info("event saved before commit {}",event.getId());
    	return eventDTO;
    }
    
	private void setDefaultValues(Event event) {
		if(event.getStatus()==null){
			event.setStatus(EventStatus.DRAFT);
		}
	}

	@Transactional(readOnly = true)
    public EventDTO getEvenDTO(Long id) throws EventNotFoundException{
    	if(id == null) throw new IllegalArgumentException("Input Event id is null");
    	Event event = eventRepository.findOne(id);
    	if(event == null) throw new EventNotFoundException();
    	EventDTO eventDTO = new EventDTO();
    	eventpoolMapper.mapEventDTO(event, eventDTO);
    	return eventDTO;
    }

	public List<String> checkEventUrl(String eventUrl) {
		Media media = eventMediaRepository.findEventUrl(eventUrl);
		if(media == null){
			List<String> suggestions = new ArrayList<String>();
			suggestions.add(eventUrl);
		}
		return new ArrayList<String>();
	}

	@Transactional(readOnly = true)
	public List<EventDTO> getAllEvents(Long userId) throws Exception {
		List<Event> allEvents = eventRepository.getAllEvents(userId);
		List<EventDTO> eventDtoList = new ArrayList<EventDTO>();
		if(allEvents!=null && allEvents.size()>0){
			for(Event event:allEvents){
				EventDTO eventDTO = new EventDTO();
				eventpoolMapper.mapEventDTO(event, eventDTO);
				eventDtoList.add(eventDTO);
			}
		}
		return eventDtoList;
	}

	@Transactional(readOnly = true)
	public EventDTO getEventByUrl(String eventUrl) throws Exception {
		Event event = eventRepository.getByEventUrl(eventUrl);
		EventDTO eventDTO = new EventDTO();
		eventpoolMapper.mapEventDTO(event, eventDTO);
		return eventDTO;
	}

	public void updateEventSettings(EventSettings settings) {
		eventSettingsRepository.save(settings);
		
	}

	public EventSettings getEventSettings(Long eventId) {
		return eventSettingsRepository.findOne(eventId);
		
	}

	@Transactional(readOnly = true)
	public List<EventDTO> getAllEvents(EventStatus status) throws Exception {
		List<Event> allEvents = eventRepository.getAllEvents(status);
		List<EventDTO> eventDtoList = new ArrayList<EventDTO>();
		if(allEvents!=null && allEvents.size()>0){
			for(Event event:allEvents){
				EventDTO eventDTO = new EventDTO();
				eventpoolMapper.mapEventDTO(event, eventDTO);
				eventDtoList.add(eventDTO);
			}
		}
		return eventDtoList;
	}

	@Transactional(readOnly = true)
	public List<EventDTO> getAllEvents(Long userId, EventStatus status)
			throws Exception {
		List<Event> allEvents = eventRepository.getAllEvents(userId,status);
		List<EventDTO> eventDtoList = new ArrayList<EventDTO>();
		if(allEvents!=null && allEvents.size()>0){
			for(Event event:allEvents){
				EventDTO eventDTO = new EventDTO();
				eventpoolMapper.mapEventDTO(event, eventDTO);
				eventDtoList.add(eventDTO);
			}
		}
		return eventDtoList;
	}

	public List<SuborderDTO> getOrderedTickets(Long userId) {
		List<Suborder> suborders = suborderRepository.getSuborders(userId);
		List<SuborderDTO> suborderDTOs = new ArrayList<SuborderDTO>();
		if(suborders!=null && suborders.size()>0){
			for(Suborder suborder:suborders){
				SuborderDTO suborderDTO = new SuborderDTO();
				eventpoolMapper.mapSuborderDTO(suborder, suborderDTO);
				suborderDTOs.add(suborderDTO);
			}
		}
		return suborderDTOs;
	}
}
