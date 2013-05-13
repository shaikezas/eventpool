package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.MediaDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;
import com.eventpool.event.module.EventApi;

/**
 * Created with IntelliJ IDEA.
 * User: xvitcoder
 * Date: 12/21/12
 * Time: 12:21 AM
 */
@Service("EventService")
public class EventServiceImpl implements EventService {
    private static List<EventDTO> eventList = new ArrayList<EventDTO>();
    private static Long id = 0L;

    @Resource
    private EventApi eventApi;
    
    public List<EventDTO> getAllEvents() {
        return eventList;
    }

    public EventDTO getEventById(Long id) {
        return findEventById(id);
    }

    public void addEvent(EventDTO event) {
    	eventList.add(event);
    	id++;
    	
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
    	event.setTitle("Test Event");
    	
    	MediaDTO mediaDTO = new MediaDTO();
    	mediaDTO.setBannerUrl("20130509_070251.jpg");
    	mediaDTO.setFaceBookUrl("facebook");
    	mediaDTO.setOtherUrl1("otherurl1");
    	mediaDTO.setEventUrl("testurl");
    	
    	event.setMedia(mediaDTO);
    	
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

    	eventApi.saveEventDTO(event);
    }

    public void deleteEventById(Long id) {
        EventDTO foundEvent = findEventById(id);
        if (foundEvent != null) {
            eventList.remove(foundEvent);
            id--;
        }
    }

    public void deleteAll() {
        eventList.clear();
        id = 0L;
    }

    public void updateEvent(EventDTO event) {
        EventDTO foundEvent = findEventById(event.getId());
        if (foundEvent != null) {
            eventList.remove(foundEvent);
            eventList.add(event);
        }
    }

    private EventDTO findEventById(Long id) {
        for (EventDTO event : eventList) {
            if (event.getId() == id) {
                return event;
            }
        }

        return null;
    }
    
    private EventDTO findEventByUrl(String eventUrl) {
        for (EventDTO event : eventList) {
            if (event.getMedia()!=null && event.getMedia().getEventUrl()!=null && event.getMedia().getEventUrl().equalsIgnoreCase(eventUrl)) {
                return event;
            }
        }

        return null;
    }

	public EventDTO getEventByUrl(String eventUrl) {
		return findEventByUrl(eventUrl);
	}
}
