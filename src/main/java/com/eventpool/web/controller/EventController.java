package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.web.domain.PhotoWeb;
import com.eventpool.web.domain.UploadedFileResponse;
import com.eventpool.web.forms.EventForm;
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
    		for(TicketForm ticket :event.getTickets()){
    			System.out.println("Ticket name :"+ticket.getName());
    		}
    	
    	
    	}else
    		System.out.println("tickets are null");
    	
//        eventService.addEvent(event);
    }
    
    /*@RequestMapping(value = "/bannerpic", method = RequestMethod.POST)
    public @ResponseBody
    UploadedFileResponse setProfilePic(@RequestParam("files[]") MultipartFile fileupload) {
    	UploadedFileResponse response = new UploadedFileResponse();
    	List<PhotoWeb> photosData = new ArrayList<PhotoWeb>();
    	PhotoWeb photo = new PhotoWeb();
    	photo.setUniqueid("123456");
    	photosData.add(photo);
    	response.setStatus(true);
    	response.setFilesuploaded(photosData);
    	System.out.println("File uploaded");
    	return response;
    }*/

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
    
    @RequestMapping(value="/{eventUrl}", method = RequestMethod.GET)
    public ModelAndView getEventByUrl(@PathVariable String eventUrl) {
    	System.out.println("Calling getEventByUrl");
    	 ModelAndView  modelView = new ModelAndView("event/eventpage");
    	 ModelMap modelMap = modelView.getModelMap();
    	 	modelMap.put("name", "ezas");
    	 	return modelView;
    }

}

