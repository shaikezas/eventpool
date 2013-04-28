package com.eventpool.event.module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.repositories.EventRepository;

@SuppressWarnings("rawtypes")
@Component
public class EventApi {

    private static final Logger logger = LoggerFactory.getLogger(EventApi.class);
	
    @Resource
    private EventMapper eventMapper;
    
    @Resource
    private EventRepository eventRepository;

    @Transactional
    public void saveEventDTO(EventDTO eventDTO){
    	Event event = new Event();
    	eventMapper.mapEvent(eventDTO, event);
    	eventRepository.save(event);
    }
}
