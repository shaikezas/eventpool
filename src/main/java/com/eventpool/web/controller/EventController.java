package com.eventpool.web.controller;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventInfoSettings;
import com.eventpool.common.dto.EventOrderSettings;
import com.eventpool.common.dto.EventSettingsDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.QuestionType;
import com.eventpool.common.type.TicketType;
import com.eventpool.event.service.impl.EventDefaultSettingsService;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.web.forms.Dropdown;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.EventFormSettings;
import com.eventpool.web.forms.EventQuestion;
import com.eventpool.web.forms.MyEventForm;
import com.eventpool.web.forms.TicketForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    EventDefaultSettingsService infoService;
    
    @Resource
    private TicketInventoryService ticketInventoryService;

    @RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public @ResponseBody void addEvent(@RequestBody EventForm event) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

    @RequestMapping(value = "/updateEvent", method = RequestMethod.PUT)
    public @ResponseBody void updateEvent(@RequestBody EventDTO event) throws Exception {
        eventService.addEvent(event);
    }

    
    @RequestMapping("/eventlist")
    public @ResponseBody List<MyEventForm> getEventList() throws Exception {
    	System.out.println("getEventList");
         List<EventDTO> eventDTOs = eventService.getAllEvents(1L);
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
    
    @RequestMapping(value = "/removeEvent/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void removeEvent(@PathVariable("id") Long id) {
      //  eventService.deleteEventById(id);
    }

    @RequestMapping(value = "/removeAllEvents", method = RequestMethod.DELETE)
    public @ResponseBody void removeAllEvents() {
        //eventService.deleteAll();
    }
    
    @RequestMapping(value="/{eventurl}", method = RequestMethod.GET)
    public ModelAndView getEventByUrl(@PathVariable String eventurl) {
    	System.out.println("Calling getEventByUrl");
    	ModelAndView  modelView = new ModelAndView("redirect:/#/event/"+eventurl);
    	 	return modelView;
    }
    
    @RequestMapping(value="/myevent/{eventId}", method = RequestMethod.GET)
    public @ResponseBody EventForm getMyEvent(@PathVariable Long eventId) throws EventNotFoundException {
    	System.out.println("Calling getMyEvent");
    	EventForm form = new EventForm();
    	EventDTO eventDTO = eventService.getEventById(eventId);
    	mapper.mapEventForm(eventDTO, form);
    	 	return form;
    }
    
    @RequestMapping(value="/myeventurl/{eventurl}", method = RequestMethod.GET)
    public @ResponseBody EventForm getMyEventUrl(@PathVariable String eventurl) throws Exception {
    	System.out.println("Calling getMyEventUrl");
    	EventForm form = new EventForm();
    	EventDTO eventDTO = eventService.getEventByUrl(eventurl);
    	mapper.mapEventForm(eventDTO, form);
    	String qty = "0";
    	List<Dropdown> dropdownList = null;
    	Dropdown dropdown = null;
    	if(eventDTO.getEventSettingsDTO()!=null && eventDTO.getEventSettingsDTO().getOrderFromSettings()!=null){
    		if(getEventOrderSettings(eventDTO).getRegistrationLimit()>=0){
    			form.setRegistrationLimit(getEventOrderSettings(eventDTO).getRegistrationLimit());
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
    
    @RequestMapping(value="/settings/{eventid}", method = RequestMethod.GET)
    public @ResponseBody EventFormSettings getEventSettings(@PathVariable Long eventid) throws Exception {
    	System.out.println("Calling getEventSettings ..."+eventid);
    	EventDTO event = eventService.getEventById(eventid);
    	EventFormSettings form = new EventFormSettings();
    	form.setEventId(eventid);
    	form.setMap(getEventInfoSettings(event));
    	form.setInfoType(event.getInfoType());
    	form.setOrderSettings(getEventOrderSettings(event));
    	
    	return form;
    }
    
    
    @RequestMapping(value = "/updatesettings", method = RequestMethod.POST)
    public @ResponseBody String updateEventSettings(@RequestBody EventFormSettings form) throws Exception {
    	System.out.println("Calling updateEventSettings ...");
    	
    	Map<String, List<EventInfoSettings>> map = form.getMap();
    	Gson gson = new Gson();
    	List<EventInfoSettings> eventInfoSettings = new ArrayList<EventInfoSettings>();
    	for (Map.Entry<String,  List<EventInfoSettings>> entry : map.entrySet()) {
    			List<EventInfoSettings> infoSettings = map.get(entry.getKey());
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
    
//    	System.out.println("JSON...Text "+getJsonString(eventInfoSettings).toString());
    	
    	/*EventDTO event = eventService.getEventById(form.getEventId());
    	event.setInfoType(form.getInfoType());
    	if(eventInfoSettings.size()>0){
    		event.getEventSettingsDTO().setEventInfoSettings(getJsonString(eventInfoSettings).toString());
    	}*/
    	
    	
    	
    	
//    	form.setMap(infoService.getEventInfoSettings());
    	
    	 	return "";
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
    
    public Map<String,List<EventInfoSettings>>  getEventInfoSettings(EventDTO event){
    	 Map<String,List<EventInfoSettings>> map = new LinkedHashMap<String,List<EventInfoSettings>>();
    	List<EventInfoSettings> settings = infoService.getEventInfoSettings();
    	List<EventInfoSettings> eventInfoSettings = new ArrayList<EventInfoSettings>();
    	eventInfoSettings.addAll(settings);
    	
    	if(event.getEventSettingsDTO()!=null && event.getEventSettingsDTO().getEventInfoSettings()!=null){
    		String infoSettings = event.getEventSettingsDTO().getEventInfoSettings();
    		Gson gson = new Gson();
    		Type type = new TypeToken<List<EventInfoSettings>>(){}.getType();
    		List<EventInfoSettings> eventSettings = gson.fromJson(infoSettings, type);
    		Collections.copy(eventInfoSettings, eventSettings);
    	}
    	
			JSONObject json = new JSONObject();
			System.out.println(json.toString());
			for(EventInfoSettings setting : eventInfoSettings){
				if(map.get(setting.getGroup())==null){
					map.put(setting.getGroup(), new ArrayList<EventInfoSettings>());
				}
				
				map.get(setting.getGroup()).add(setting);
			}
		return map;
		
		
	} 
    
    
    public EventOrderSettings  getEventOrderSettings(EventDTO event){
    	if(event.getEventSettingsDTO()!=null && event.getEventSettingsDTO().getOrderFromSettings()!=null){
    	String orderFromSettings = event.getEventSettingsDTO().getOrderFromSettings();
    	 Gson gson = new Gson();
    	 Type type = new TypeToken<EventOrderSettings>(){}.getType();
    	 EventOrderSettings orderSettings = gson.fromJson(orderFromSettings, type);
    	 
    	return orderSettings;
    	}
    	return new EventOrderSettings();
	} 
    
    /*private JSONObject getJsonString(List<EventInfoSettings> infoSettings) throws JSONException{
    	    JSONObject responseDetailsJson = new JSONObject();
    	    JSONArray jsonArray = new JSONArray(infoSettings);
    	    responseDetailsJson.put("info",jsonArray);
    	    return responseDetailsJson;

    }*/
    

    /*List<Student> students = new ArrayList<Student>();
    students.add(a);
    students.add(b);
    students.add(c);
    students.add(d);

    gson = new Gson();
    String jsonStudents = gson.toJson(students);
    System.out.println("jsonStudents = " + jsonStudents);

    //
    // Converts JSON string into a collection of Student object.
    //
    Type type = new TypeToken<List<Student>>(){}.getType();
    List<Student> studentList = gson.fromJson(jsonStudents, type);*/
}

