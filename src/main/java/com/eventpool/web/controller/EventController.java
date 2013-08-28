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
import com.eventpool.common.dto.InvoiceDTO;
import com.eventpool.common.dto.Region;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDTO;
import com.eventpool.common.entities.User;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.exceptions.TicketNotFoundException;
import com.eventpool.common.module.DateCustomConverter;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.module.HtmlEmailService;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.QuestionType;
import com.eventpool.common.type.TicketType;
import com.eventpool.event.service.impl.EventSettingsService;
import com.eventpool.event.service.impl.TicketAttendeeDTO;
import com.eventpool.event.service.impl.TicketBuyerDTO;
import com.eventpool.order.service.InvoiceService;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.Dropdown;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.EventFormSettings;
import com.eventpool.web.forms.EventQuestion;
import com.eventpool.web.forms.MyEventForm;
import com.eventpool.web.forms.SubOrderForm;
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
    InvoiceService invoiceService;

    @Resource
    private TicketInventoryService ticketInventoryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DateCustomConverter dateConverter;

    @Resource
    private HtmlEmailService htmlEmailService;
    
    @Resource
    private EntityUtilities  entityUtilities;
    
    @RequestMapping(value = "/myevent/addevent", method = RequestMethod.POST)
    public @ResponseBody ResponseMessage  addEvent(@RequestBody EventForm event) throws Exception {
    	User user = userService.getCurrentUser();
    	
    	if(event.getTickets()!=null){
    		int i=1;
    		for(TicketForm ticket :event.getTickets()){
    			ticket.setCreatedBy(user.getId());
    			ticket.setTicketOrder(i);
    			i++;
    		}
    	
    	
    	}else
    		System.out.println("tickets are null");
    	
    	System.out.println("user Id "+user.getId());
    	EventDTO eventDTO = new EventDTO();
    	mapper.mapEventDTO(event, eventDTO);
    	eventDTO.setCreatedBy(user.getId());
//    	updateEventType(eventDTO);
    	 try {
 			eventService.addEvent(eventDTO);
 			String email = user.getEmail();
 			String subject = eventDTO.getEventUrl();
 			List<String> toList = new ArrayList<String>();
 			toList.add(email);
 			//TODO remove comment.
// 			htmlEmailService.sendMail(toList, subject, subject+" Successfully created.", null);
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
    	User user = userService.getCurrentUser();
        System.out.println("logged in name :"+user.getUserName());
    }

    
    @RequestMapping("/myevent/draftEventList")
    public @ResponseBody List<MyEventForm> getDraftEventList() throws Exception {
    	User user = userService.getCurrentUser();
    	List<EventDTO> eventDTOs = eventService.getAllEvents(user.getId(),EventStatus.DRAFT);
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
    	User user = userService.getCurrentUser();
        List<EventDTO> eventDTOs = eventService.getAllEvents(user.getId(),EventStatus.OPEN);
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
    	User user = userService.getCurrentUser();
    	  List<EventDTO> eventDTOs = eventService.getAllEvents(user.getId(),EventStatus.CLOSED);
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
    
    @RequestMapping(value = "/myevent/print/{suborderid}", method = RequestMethod.GET)
    public @ResponseBody InvoiceDTO printEvent(@PathVariable("suborderid") Long suborderId) throws Exception {
    	return invoiceService.viewInvoice(suborderId);
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
    	
    	Map<Integer, Region> csc = entityUtilities.getCitiesWithStateAndCountry();
    	Region region = csc.get(form.getCityId());
    	form.setCityName(region.getCityName());
    	form.setStateName(region.getStateName());
    	form.setCountryName(region.getCountryName());
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
    
    @RequestMapping(value = "/myevent/getTicketHistory/{eventid}", method = RequestMethod.GET)
    public @ResponseBody List<TicketInventoryDTO> getTicketHistory(@PathVariable("eventid") Long eventId) throws Exception {
    	//List<SuborderDTO> subOrdersList = 
    			return eventService.getTicketInventoryDetails(eventId);
    			//getEventOrderedTickets(eventId);
    }
    
    @RequestMapping(value = "/myevent/attendees/{ticketId}", method = RequestMethod.GET)
    public @ResponseBody TicketAttendeeDTO getAttendees(@PathVariable("ticketId") Long ticketId) throws Exception {
    	 			return infoService.getAttendes(ticketId);
    }
    
    @RequestMapping(value = "/myevent/buyers/{ticketId}", method = RequestMethod.GET)
    public @ResponseBody TicketBuyerDTO getBuyers(@PathVariable("ticketId") Long ticketId) throws Exception {
    				return infoService.getBuyers(ticketId);
    }
    
    @RequestMapping(value = "/myevent/myTickets", method = RequestMethod.GET)
    public @ResponseBody List<SubOrderForm> getMyTickets() throws Exception {
    			List<SuborderDTO> subOrdersList = eventService.getOrderedTickets((userService.getCurrentUser()).getId());
    			return converSubDTOtoForm(subOrdersList);
    }
    

	private List<SubOrderForm> converSubDTOtoForm(List<SuborderDTO> subOrdersList) throws EventNotFoundException, TicketNotFoundException {
		List<SubOrderForm> subFormList = new ArrayList<SubOrderForm>();
		for(SuborderDTO subOrder : subOrdersList){
			SubOrderForm subForm = new SubOrderForm();
			subForm.setQuantity(subOrder.getTicket().getQuantity());
			subForm.setTicketName(subOrder.getTicketName());
			subForm.setTicketPrice(subOrder.getTicketPrice());
			EventDTO event = eventService.getEventById(subOrder.getTicket().getEventId());
			subForm.setEvnetName(event.getTitle());
			subForm.setStartDate(dateConverter.convertFrom(event.getStartDate()));
			subForm.setEndDate(dateConverter.convertFrom(event.getEndDate()));
			subForm.setBookedOn(dateConverter.convertFrom(subOrder.getCreatedDate()));
			subForm.setSuborderId(subOrder.getId());
			subForm.setOrderId(subOrder.getOrder().getId());
			subFormList.add(subForm);
			
		}
		return subFormList;
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
    	form.setEventUrl(dto.getEventUrl());
    	
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

