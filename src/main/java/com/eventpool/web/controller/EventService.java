package com.eventpool.web.controller;

import java.util.List;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.exceptions.EventNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:20 AM
 */
public interface EventService {
    public List<EventDTO> getAllEvents(Long userId) throws Exception;

    public EventDTO getEventById(Long id) throws EventNotFoundException;

    public boolean addEvent(EventDTO Event) throws Exception;

    public EventDTO getEventByUrl(String eventUrl) throws Exception;
    
    public boolean publishEvent(Long eventId,boolean isPublish) throws Exception;
    
    public boolean publishEvent(String eventUrl,boolean isPublish) throws Exception;
}
