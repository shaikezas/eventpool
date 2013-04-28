package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.event.module.EventApi;
import com.eventpool.event.module.EventMapper;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:21 AM
 */
@Service("EventService")
public class EventServiceImpl implements EventService {
    private static List<EventDTO> eventList = new ArrayList<EventDTO>();
    private static Long id = 0L;

    @Resource
    private EventApi eventApi;
    
    public List<EventDTO> getAllEvents() {
        return eventList;
    }

    public EventDTO getEventById(Long id) {
        return findEventById(id);
    }

    public void addEvent(EventDTO event) {
    	eventList.add(event);
    	id++;
    	//eventApi.saveEventDTO(event);
    }

    public void deleteEventById(Long id) {
        EventDTO foundEvent = findEventById(id);
        if (foundEvent != null) {
            eventList.remove(foundEvent);
            id--;
        }
    }

    public void deleteAll() {
        eventList.clear();
        id = 0L;
    }

    public void updateEvent(EventDTO event) {
        EventDTO foundEvent = findEventById(event.getId());
        if (foundEvent != null) {
            eventList.remove(foundEvent);
            eventList.add(event);
        }
    }

    private EventDTO findEventById(Long id) {
        for (EventDTO event : eventList) {
            if (event.getId() == id) {
                return event;
            }
        }

        return null;
    }
}
