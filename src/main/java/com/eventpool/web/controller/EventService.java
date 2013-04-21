package com.eventpool.web.controller;

import java.util.List;

import com.eventpool.common.entities.Event;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:20 AM
 */
public interface EventService {
    public List<Event> getAllEvents();

    public Event getEventById(Long id);

    public void addEvent(Event Event);

    public void deleteEventById(Long id);

    public void deleteAll();

    public void updateEvent(Event Event);
}
