package com.eventpool.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.TicketForm;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:22 AM
 */
@Controller
@RequestMapping("/events")
public class EventController {

    @Resource(name="EventService")
    private EventService eventService;
    
    
    private EventForm eventForm;
    
    @RequestMapping("eventslist.json")
    public @ResponseBody EventForm getEventList() {
        return eventForm;
    }

    @RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public @ResponseBody void addEvent(@RequestBody EventForm event) {
    	System.out.println("Event title :"+event.getTitle());
    	System.out.println("description  :"+event.getDescription());
    	System.out.println("Start date "+event.getStartDate());
    	System.out.println("End Date "+event.getEndDate());
    	System.out.println("Is Private "+event.getIsPrivate());
    	System.out.println("Banner "+event.getBanner());
    	System.out.println("Thumbnal "+event.getOrganizerLogo());
    	System.out.println("Event url "+event.getEventUrl());
    	if(event.getTickets()!=null){
    	System.out.println("Ticket size"+event.getTickets().size());
    	System.out.println("Ticket name"+event.getTickets().get(0).getName());
    	}else
    		System.out.println("tickets are null");
    	
//        eventService.addEvent(event);
    }
    
    @RequestMapping(value = "/addTicket", method = RequestMethod.POST)
    public @ResponseBody void addTicket(@RequestBody EventForm event) {
    	
    	System.out.println("Adding Ticket");
    	System.out.println("Event title :"+event.getTitle());
    	System.out.println("description  :"+event.getDescription());
    	System.out.println("Start date "+event.getStartDate());
    	System.out.println("End Date "+event.getEndDate());
    	System.out.println("Is Private "+event.getIsPrivate());
    	System.out.println("Banner "+event.getBanner());
    	System.out.println("Thumbnal "+event.getOrganizerLogo());
    	System.out.println("Event url "+event.getEventUrl());
    	System.out.println("Ticket size"+event.getTickets().size());
    	if(event.getTickets().size()>0)
    		System.out.println("Ticket name"+event.getTickets().get(0).getName());
    	else
    		System.out.println("Tickets are empty");
    	
    	TicketForm form = new TicketForm();
    	event.getTickets().add(form);
    	System.out.println("Added form");
    	this.eventForm = event;
    	
//        eventService.addEvent(event);
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public @ResponseBody void updateEvent(@RequestBody EventDTO event) {
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
    	System.out.println("Create Eent");
        return "events/createevent";
    }
    
    @RequestMapping("/eventlist")
    public String getEventListPartialPage(ModelMap modelMap) {
    	System.out.println("Event list");
        return "events/eventlist";
    }
    
    @RequestMapping("/myevents")
    public String getMyEventListPartialPage(ModelMap modelMap) {
        return "events/myevents";
    }
    

    @RequestMapping("/findevent")
    public String getFindEventPartialPage(ModelMap modelMap) {
        return "events/findevent";
    }
    
    @RequestMapping("/mytickets")
    public String getMyTicketsPartialPage(ModelMap modelMap) {
        return "ticket/mytickets";
    }
    
}
