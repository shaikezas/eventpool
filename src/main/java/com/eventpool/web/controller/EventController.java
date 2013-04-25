package com.eventpool.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.entities.Event;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:22 AM
 */
@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping("eventslist.json")
    public @ResponseBody List<Event> getEventList() {
    	System.out.println("Getting events "+eventService.getAllEvents().size());
        return eventService.getAllEvents();
    }

    @RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public @ResponseBody void addEvent(@RequestBody Event event) {
    	System.out.println("Event name :"+event.getTitle());
    	System.out.println("Adding event  :"+event.toString());
        eventService.addEvent(event);
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public @ResponseBody void updateEvent(@RequestBody Event event) {
        eventService.updateEvent(event);
    }

    @RequestMapping(value = "/removeEvent/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeEvent(@PathVariable("id") Long id) {
        eventService.deleteEventById(id);
    }

    @RequestMapping(value = "/removeAllEvents", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllEvents() {
        eventService.deleteAll();
    }

    @RequestMapping("/createevent")
    public String getCreateEventPartialPage(ModelMap modelMap) {
        return "events/createevent";
    }
    
    @RequestMapping("/eventlist")
    public String getEventListPartialPage(ModelMap modelMap) {
        return "events/eventlist";
    }
}
