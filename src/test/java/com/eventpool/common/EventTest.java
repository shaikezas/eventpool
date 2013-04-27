package com.eventpool.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;

public class EventTest extends BaseTest{
	
	@Resource
	EventRepository eventRepository;
	
	 
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
    

}
