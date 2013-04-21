package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eventpool.common.entities.Event;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:21 AM
 */
@Service("EventService")
public class EventServiceImpl implements EventService {
    private static List<Event> eventList = new ArrayList<Event>();
    private static Long id = 0L;

    public List<Event> getAllEvents() {
        return eventList;
    }

    public Event getEventById(Long id) {
        return findEventById(id);
    }

    public void addEvent(Event Event) {
        Event.setId(++id);
        eventList.add(Event);
    }

    public void deleteEventById(Long id) {
        Event foundEvent = findEventById(id);
        if (foundEvent != null) {
            eventList.remove(foundEvent);
            id--;
        }
    }

    public void deleteAll() {
        eventList.clear();
        id = 0L;
    }

    public void updateEvent(Event Event) {
        Event foundEvent = findEventById(Event.getId());
        if (foundEvent != null) {
            eventList.remove(foundEvent);
            eventList.add(Event);
        }
    }

    private Event findEventById(Long id) {
        for (Event Event : eventList) {
            if (Event.getId() == id) {
                return Event;
            }
        }

        return null;
    }
}
