package com.eventpool.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.dto.EventSettingsDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.QuestionType;
import com.eventpool.common.type.TicketType;
import com.eventpool.event.service.impl.EventSettingsService;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.forms.Dropdown;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.EventFormSettings;
import com.eventpool.web.forms.EventQuestion;
import com.eventpool.web.forms.MyEventForm;
import com.eventpool.web.forms.TicketForm;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:22 AM
 */
@Controller
@RequestMapping("/event")
public class EventController {

	@Autowired
    private EventService eventService;
    
    @Resource
    private EventpoolMapper mapper;
    
    @Resource
    EventSettingsService infoService;
    
    @Resource
    private TicketInventoryService ticketInventoryService;

    @RequestMapping(value = "/myevent/addevent", method = RequestMethod.POST)
    public @ResponseBody ResponseMessage  addEvent(@RequestBody EventForm event) throws Exception {
    	if(event.getTickets()!=null){
    		int i=1;
    		for(TicketForm ticket :event.getTickets()){
    			ticket.setCreatedBy(1L);
    			ticket.setTicketOrder(i);
    			i++;
    		}
    	
    	
    	}else
    		System.out.println("tickets are null");
    	
    	EventDTO eventDTO = new EventDTO();
    	mapper.mapEventDTO(event, eventDTO);
    	eventDTO.setCreatedBy(1L);
//    	updateEventType(eventDTO);
    	 try {
 			eventService.addEvent(eventDTO);
 			return new ResponseMessage(ResponseMessage.Type.success, "Successfully created event");
 		} catch (Exception e) {
 			e.printStackTrace();
 			return new ResponseMessage(ResponseMessage.Type.error, "Failed to create event : reason -"+e.getMessage());
 		}
    }
    

    @RequestMapping(value = "/myevent/updateevent", method = RequestMethod.PUT)
    public @ResponseBody ResponseMessage updateEvent(@RequestBody EventDTO event) throws Exception {
        eventService.addEvent(event);
        return new ResponseMessage(ResponseMessage.Type.success, "Successfully updated event");
    }
    
    @RequestMapping(value = "/myevent/createevent", method = RequestMethod.POST)
    public @ResponseBody void createEvent() throws Exception {
    	System.out.println("createEvent()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name1 = auth.getName(); //get logged in username
        System.out.println("logged in name1 :"+name1);
    	   User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	      String name = user.getUsername(); //get logged in username
        System.out.println("logged in name :"+name);
    }

    
    @RequestMapping("/myevent/draftEventList")
    public @ResponseBody List<MyEventForm> getDraftEventList() throws Exception {
         List<EventDTO> eventDTOs = eventService.getAllEvents(1L,EventStatus.DRAFT);
         List<MyEventForm>  forms = new ArrayList<MyEventForm>();
         MyEventForm form  = null;
         String sold = "";
         for(EventDTO dto : eventDTOs){
        	 form =  convertToMyEventForm(dto);
        	 sold = ticketInventoryService.getAggregateTicketInventoryByEvent(dto.getId());
        	 form.setSold(sold);
        	 forms.add(form);
         }
         
         
         
         return forms;
    }
    
    @RequestMapping("/myevent/liveEventList")
    public @ResponseBody List<MyEventForm> getLiveEventList() throws Exception {
        List<EventDTO> eventDTOs = eventService.getAllEvents(1L,EventStatus.OPEN);
        List<MyEventForm>  forms = new ArrayList<MyEventForm>();
        MyEventForm form  = null;
        String sold = "";
        for(EventDTO dto : eventDTOs){
       	 form =  convertToMyEventForm(dto);
       	 sold = ticketInventoryService.getAggregateTicketInventoryByEvent(dto.getId());
       	 form.setSold(sold);
       	 forms.add(form);
        }
         
         return forms;
        
    }
    
    @RequestMapping("/myevent/pastEventList")
    public @ResponseBody List<MyEventForm> getPastEventList() throws Exception {
    	  List<EventDTO> eventDTOs = eventService.getAllEvents(1L,EventStatus.CLOSED);
          List<MyEventForm>  forms = new ArrayList<MyEventForm>();
          MyEventForm form  = null;
          String sold = "";
          for(EventDTO dto : eventDTOs){
         	 form =  convertToMyEventForm(dto);
         	 sold = ticketInventoryService.getAggregateTicketInventoryByEvent(dto.getId());
         	 form.setSold(sold);
         	 forms.add(form);
          }
         
         
         return forms;
    }
    @RequestMapping(value = "/myevent/removeEvent/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeEvent(@PathVariable("id") Long id) {
      //  eventService.deleteEventById(id);
    }
    
    @RequestMapping(value = "/myevent/publishevent/{eventid}", method = RequestMethod.GET)
    public @ResponseBody ResponseMessage publishEvent(@PathVariable("eventid") Long eventId) throws Exception {
        eventService.publishEvent(eventId, true);
        return new ResponseMessage(ResponseMessage.Type.success, "Successfully published event");
    }

    @RequestMapping(value = "/myevent/removeAllEvents", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllEvents() {
        //eventService.deleteAll();
    }
    
    @RequestMapping(value="/{eventurl}", method = RequestMethod.GET)
    public ModelAndView getEventByUrl(@PathVariable String eventurl) {
    	System.out.println("Calling getEventByUrl");
    	ModelAndView  modelView = new ModelAndView("redirect:/#/event/"+eventurl);
    	 	return modelView;
    }
    
    @RequestMapping(value="/id/{eventId}", method = RequestMethod.GET)
    public @ResponseBody EventForm getMyEvent(@PathVariable Long eventId) throws EventNotFoundException {
    	System.out.println("Calling getMyEvent");
    	EventForm form = new EventForm();
    	EventDTO eventDTO = eventService.getEventById(eventId);
    	mapper.mapEventForm(eventDTO, form);
    	 	return form;
    }
    
    @RequestMapping(value="/url/{eventurl}", method = RequestMethod.GET)
    public @ResponseBody EventForm getMyEventUrl(@PathVariable String eventurl) throws Exception {
    	System.out.println("Calling getMyEventUrl");
    	EventForm form = new EventForm();
    	EventDTO eventDTO = eventService.getEventByUrl(eventurl);
    	mapper.mapEventForm(eventDTO, form);
    	String qty = "0";
    	List<Dropdown> dropdownList = null;
    	Dropdown dropdown = null;
    	if(eventDTO.getEventSettingsDTO()!=null && eventDTO.getEventSettingsDTO().getOrderFromSettings()!=null){
    		if(infoService.getEventOrderSettings(eventDTO).getRegistrationLimit()>=0){
    			form.setRegistrationLimit(infoService.getEventOrderSettings(eventDTO).getRegistrationLimit());
    		}
    	}
    	for(TicketForm ticket : form.getTickets()){
    		dropdownList = new ArrayList<Dropdown>();
    		ticket.setQtyList(dropdownList);
    		dropdown = new Dropdown(qty,qty);
    		dropdownList.add(dropdown);
    		for(int i=ticket.getMinQty();i<=ticket.getMaxQty();i++){
    			dropdown = new Dropdown(String.valueOf(i),String.valueOf(i));
        		dropdownList.add(dropdown);
    		}
    		
    		
    	}
    	 	return form;
    }
    
    @RequestMapping(value="/myevent/settings/{eventid}", method = RequestMethod.GET)
    public @ResponseBody EventFormSettings getEventSettings(@PathVariable Long eventid) throws Exception {
    	System.out.println("Calling getEventSettings ..."+eventid);
    	EventDTO event = eventService.getEventById(eventid);
    	EventFormSettings form = new EventFormSettings();
    	form.setEventId(eventid);
    	form.setMap(getEventInfoSettings(event));
    	form.setInfoType(event.getInfoType());
    	form.setOrderSettings(infoService.getEventOrderSettings(event));
    	
    	return form;
    }
    
    
    @RequestMapping(value = "/myevent/updatesettings", method = RequestMethod.POST)
    public @ResponseBody ResponseMessage updateEventSettings(@RequestBody EventFormSettings form) throws Exception {
    	System.out.println("Calling updateEventSettings ...");
    	
    	Map<String, LinkedList<EventInfoSettings>> map = form.getMap();
    	Gson gson = new Gson();
    	List<EventInfoSettings> eventInfoSettings = new LinkedList<EventInfoSettings>();
    	for (Map.Entry<String,  LinkedList<EventInfoSettings>> entry : map.entrySet()) {
    		LinkedList<EventInfoSettings> infoSettings = map.get(entry.getKey());
    			for(EventInfoSettings info : infoSettings ){
    				if(info.getIsValue()){
    					eventInfoSettings.add(info);
    				}
    			}
    	}
    	EventDTO eventDTO = eventService.getEventById(form.getEventId());
    	if(eventDTO.getEventSettingsDTO()==null){
    		eventDTO.setEventSettingsDTO(new EventSettingsDTO());
    	}
    	eventDTO.setInfoType(form.getInfoType());
    	eventDTO.getEventSettingsDTO().setEventInfoSettings(gson.toJson(eventInfoSettings));
    	eventDTO.getEventSettingsDTO().setOrderFromSettings(gson.toJson(form.getOrderSettings()));
    	eventDTO.getEventSettingsDTO().setEventId(form.getEventId());
    	eventDTO.getEventSettingsDTO().setCreatedBy(eventDTO.getCreatedBy());
    	eventService.addEvent(eventDTO);
    
    	return new ResponseMessage(ResponseMessage.Type.success, "Successfully updated event settings");
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
    	form.setId(dto.getId());
    	form.setEventUrl(dto.getMedia().getEventUrl());
    	
    	return form;
    }
    
    private String getDateString(Date source) {
		String destination;
		String pattern = "dd-MMM-yyyy HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		destination = sdf.format(source);
		return destination;
	}
    
    
    
    private List<EventQuestion> getQuestions(){
    	List<EventQuestion> questions = new ArrayList<EventQuestion>();
    	EventQuestion question = new EventQuestion();
    	question.setQuestion("Sex");
    	question.setValue(true);
    	questions.add(question);
    	question.setType(QuestionType.RADIO);
    	
    	
    	return questions;
    }
    
    public Map<String,LinkedList<EventInfoSettings>>  getEventInfoSettings(EventDTO event){
    	 Map<String,LinkedList<EventInfoSettings>> map = new HashMap<String,LinkedList<EventInfoSettings>>();
    	List<EventInfoSettings> settings = infoService.getEventDefaultSettings();
    	 Map<String,EventInfoSettings> eventInfoSettings = new LinkedHashMap<String,EventInfoSettings>();
    	for(EventInfoSettings sett : settings){
    		eventInfoSettings.put(sett.getName(),sett);
    	}
    	 
    	if(event.getEventSettingsDTO()!=null && event.getEventSettingsDTO().getEventInfoSettings()!=null){
    		
    		List<EventInfoSettings> eventSettings = infoService.getEventInfoSettings(event);
    		for(EventInfoSettings sett : eventSettings){
        		eventInfoSettings.put(sett.getName(),sett);
        	}
    	}
    	
			for(EventInfoSettings setting : eventInfoSettings.values()){
				if(map.get(setting.getGroup())==null){
					map.put(setting.getGroup(), new LinkedList<EventInfoSettings>());
				}
				
				map.get(setting.getGroup()).add(setting);
			}
		return map;
		
		
	} 
    
    

}

