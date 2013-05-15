package com.eventpool.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.TicketType;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.MyEventForm;
import com.eventpool.web.forms.TicketForm;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:22 AM
 */
@Controller
@RequestMapping("/event")
public class EventController {

    @Resource(name="EventService")
    private EventService eventService;
    
    @Resource
    private EventpoolMapper mapper;
    
    @Resource
    private TicketInventoryService ticketInventoryService;

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
    		int i=1;
    		for(TicketForm ticket :event.getTickets()){
    			System.out.println("Ticket name :"+ticket.getName());
    			ticket.setCreatedBy(1L);
    			ticket.setTicketOrder(i);
    			i++;
    		}
    	
    	
    	}else
    		System.out.println("tickets are null");
    	
    	System.out.println("Event..."+event);
    	
    	EventDTO eventDTO = new EventDTO();
    	mapper.mapEventDTO(event, eventDTO);
    	eventDTO.setCreatedBy(1L);
    	updateEventType(eventDTO);
        try {
			eventService.addEvent(eventDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public @ResponseBody void updateEvent(@RequestBody EventDTO event) {
        eventService.updateEvent(event);
    }

    
    @RequestMapping("/eventlist")
    public @ResponseBody List<MyEventForm> getEventList() {
    	System.out.println("getEventList");
         List<EventDTO> eventDTOs = eventService.getAllEvents();
         System.out.println("Event dtos size :"+eventDTOs.size());
         List<MyEventForm>  forms = new ArrayList<MyEventForm>();
         MyEventForm form  = new MyEventForm();
         
         form.setCreatedDate(getDateString(new Date()));
         form.setEndDate(getDateString(new Date()));
         form.setStartDate(getDateString(new Date()));
         form.setTitle("Ezas Test");
         form.setStatus(EventStatus.DRAFT);
         form.setSold("0/100");
         forms.add(form);
         String sold = "";
         for(EventDTO dto : eventDTOs){
        	 form =  convertToMyEventForm(dto);
        	 forms.add(form);
         }
         
         
         
         return forms;
    }
    
    @RequestMapping(value = "/removeEvent/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeEvent(@PathVariable("id") Long id) {
        eventService.deleteEventById(id);
    }

    @RequestMapping(value = "/removeAllEvents", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllEvents() {
        eventService.deleteAll();
    }
    
    @RequestMapping(value="/{eventUrl}", method = RequestMethod.GET)
    public ModelAndView getEventByUrl(@PathVariable String eventUrl) {
    	System.out.println("Calling getEventByUrl");
    	 ModelAndView  modelView = new ModelAndView("event/eventpage");
    	 ModelMap modelMap = modelView.getModelMap();
    	 	modelMap.put("name", "ezas");
    	 	return modelView;
    }
    
    private void updateEventType(EventDTO dto){
    	Boolean isFree = false;
    	Boolean isPaid = false;
    	if(dto.getTickets()!=null){
    	for(TicketDTO ticket : dto.getTickets()){
    		if(ticket.getTicketType().equals(TicketType.FREE)){
    			isFree = true;
    		}
    		if(ticket.getTicketType().equals(TicketType.PAID)){
    			isFree = true;
    		}
    	}
    	}
    	
    	if(isFree && isPaid){
    		dto.setEventType(EventType.PAIDNFREE);
    	}else if(isFree){
    		dto.setEventType(EventType.FREE);
    	}else if(isPaid){
    		dto.setEventType(EventType.PAID);
    	}else{
    		dto.setEventType(EventType.NOREGISTRATION);
    	}
    	
    }
    
    private void updateEventForm(EventForm form){
    	if(form.getTickets()!=null){
    	for(TicketForm ticket : form.getTickets()){
    		if(ticket.getTicketType().equals(TicketType.FREE)){
    			ticket.setShowFree(true);
    		}
    		if(ticket.getTicketType().equals(TicketType.PAID)){
    			ticket.setShowPrice(true);
    		}
    	}
    	}
    }
    
    private MyEventForm convertToMyEventForm(EventDTO dto){
    	
    	MyEventForm form = new MyEventForm();
    	
    	form.setTitle(dto.getTitle());
    	form.setCreatedDate(getDateString(dto.getCreatedDate()));
    	form.setStartDate(getDateString(dto.getStartDate()));
    	form.setEndDate(getDateString(dto.getEndDate()));
    	
    	return form;
    }
    
    private String getDateString(Date source) {
		String destination;
		String pattern = "dd-MMM-yyyy HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		destination = sdf.format(source);
		return destination;
	}

}

