package com.eventpool.event.module;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.util.EventpoolMapper;

@SuppressWarnings("rawtypes")
@Component
public class EventApi {

    private static final Logger logger = LoggerFactory.getLogger(EventApi.class);
	
    @Resource
    private EventpoolMapper eventpoolMapper;
    
    @Resource
    private EventRepository eventRepository;

    @Transactional
    public void saveEventDTO(EventDTO eventDTO){
    	if(eventDTO==null) throw new IllegalArgumentException("Input event DTO is null");
    	Long id = eventDTO.getId();
    	Event event = null;
    	if(id == null){
    		event = new Event();
    	}else{
    		event = eventRepository.findOne(id);
    	}
    	eventpoolMapper.mapEvent(eventDTO, event);
    	eventRepository.save(event);
    	logger.info("event saved before commit {}",event.getId());
    }
}
