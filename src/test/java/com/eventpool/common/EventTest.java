package com.eventpool.common;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.module.CommonUtils;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.module.IPLocation;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.event.command.SaveEventCommand;
import com.eventpool.event.module.EventApi;
import com.eventpool.event.service.EventCommandService;
import com.eventpool.web.controller.EventController;

public class EventTest extends BaseTest{
	
	@Resource
	EventRepository eventRepository;
	
	@Resource
	EventpoolMapper eventMapper;
	
	@Resource
	EventApi eventApi;
	
	@Resource
	EventController eventController;
	
	@Resource
	EventCommandService eventCommandService;
	
    @Test
    @Transactional
    @Rollback(false)
    public void addEvent() {
    	Event event = new Event();
    	event.setContactDetails("contact details");
    	event.setCreatedBy(2L);
    	event.setCreatedDate(new Date());
    	event.setDescription("description");
    	event.setEndDate(new Date());
    	event.setEventType(EventType.FREE);
    	event.setInfoType(EventInfoType.BASIC);
    	event.setIsActive(true);
    	event.setModifiedDate(new Date());
    	event.setOrganizerDescription("org desc");
    	event.setOrganizerName("orgname");
    	event.setStartDate(new Date());
    	event.setStatus(EventStatus.OPEN);
    	event.setSubCategoryId(1);
    	event.setTermsAndConditions("terms and conditions");
    	event.setTitle("title");
    	Address venueAddress = new Address();
    	venueAddress.setAddress1("adr1");
    	venueAddress.setAddress2("addr2");
    	venueAddress.setCityId(1);
    	venueAddress.setFax("fax");
    	venueAddress.setMapUrl("mapUrl");
    	venueAddress.setMobileNumber("molenumbe");
    	venueAddress.setPhoneNumber("ph nber");
    	venueAddress.setZipCode(12345L);
    	event.setVenueAddress(venueAddress);
    	event.setVenueName("venue name");
    	
    	Ticket ticket = new Ticket();
    	ticket.setCreatedBy(0L);
    	ticket.setCreatedDate(new Date());
    	ticket.setDescription("descriptions");
    	ticket.setIsActive(true);
    	ticket.setMaxQty(10);
    	ticket.setMinQty(2);
    	ticket.setModifiedDate(new Date());
    	ticket.setName("name");
    	ticket.setPrice(2.3d);
    	ticket.setQuantity(100);
    	ticket.setSaleEnd(new Date());
    	ticket.setSaleStart(new Date());

    	List<Ticket> tickets = new ArrayList<Ticket>();
    	tickets.add(ticket);
    	event.setTickets(tickets);
    	eventRepository.save(event);
    }
    
    @Test
    @Transactional(readOnly=true)
    public void getEventDTO(){
    	Event event = eventRepository.findOne(1L);
    	EventDTO eventDTO = new EventDTO();
    	if(event!=null){
	    	eventMapper.mapEventDTO(event, eventDTO);
	    	log.info(eventDTO.toString());
    	}
    }

    @Resource
    IPLocation ipLocation; 
    
    @Test
    public void testIpLocation(){
    	//ipLocation.getLocation("14.99.224.122");
    }

    
    @Test
    @Transactional
    @Rollback(false)
    public void saveEventDTO(){
    	EventDTO event = getEventDTOObject();
    	//eventController.addEvent(event);
    }

	private EventDTO getEventDTOObject() {
		EventDTO event = new EventDTO();
    	event.setContactDetails("contact details");
    	event.setCreatedBy(2L);
    	event.setCreatedDate(new Date());
    	event.setDescription("description");
    	event.setEndDate(new Date());
    	event.setEventType(EventType.FREE);
    	event.setInfoType(EventInfoType.BASIC);
    	event.setIsActive(true);
    	event.setModifiedDate(new Date());
    	event.setOrganizerDescription("org desc");
    	event.setOrganizerName("orgname");
    	event.setStartDate(new Date());
    	event.setStatus(EventStatus.OPEN);
    	event.setSubCategoryId(1);
    	event.setTermsAndConditions("terms and conditions");
    	event.setTitle("title");
    	AddressDTO venueAddress = new AddressDTO();
    	venueAddress.setAddress1("adr1");
    	venueAddress.setAddress2("addr2");
    	venueAddress.setCityId(1);
    	venueAddress.setFax("fax");
    	venueAddress.setMapUrl("mapUrl");
    	venueAddress.setMobileNumber("molenumbe");
    	venueAddress.setPhoneNumber("ph nber");
    	venueAddress.setZipCode(12345L);
    	event.setVenueAddress(venueAddress);
    	event.setVenueName("venue name");
    	
    	TicketDTO ticket = new TicketDTO();
    	ticket.setCreatedBy(0L);
    	ticket.setCreatedDate(new Date());
    	ticket.setDescription("descriptions");
    	ticket.setIsActive(true);
    	ticket.setMaxQty(10);
    	ticket.setMinQty(2);
    	ticket.setModifiedDate(new Date());
    	ticket.setName("name");
    	ticket.setPrice(2.3d);
    	ticket.setQuantity(100);
    	ticket.setSaleEnd(new Date());
    	ticket.setSaleStart(new Date());

    	List<TicketDTO> tickets = new ArrayList<TicketDTO>();
    	tickets.add(ticket);
    	event.setTickets(tickets);
		return event;
	}
   
    @Resource
    private CommonUtils commonUtils;
   
    @Test
    public void timeZoneConversion(){
    	 Date gmtDate = commonUtils.getGMTDate(new Date());
    	 log.info("gmt date "+gmtDate);
    	 Date timeZoneDate = commonUtils.getTimeZoneDate(gmtDate, "IST");
    	 log.info(" local date"+timeZoneDate);
    }
    
    @Test
    public void testEventServiceCommand() throws Exception{
    	SaveEventCommand saveEventCommand = new SaveEventCommand();
    	saveEventCommand.setEventDTO(getEventDTOObject());
    	eventCommandService.executeCommand(saveEventCommand);
    }
    
    @Resource
    private EntityUtilities entityUtilities;
    
    @Test
    public void testCityMap(){
    	Map<Integer, String> citiesWithStateAndCountry = entityUtilities.getCitiesWithStateAndCountry();
    	for(String str:citiesWithStateAndCountry.values()){
    		log.info(str);
    	}
    }
}
