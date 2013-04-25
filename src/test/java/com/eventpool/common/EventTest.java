package com.eventpool.common;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Event;
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
    	venueAddress.setCityId(1L);
    	venueAddress.setFax("fax");
    	venueAddress.setMapUrl("mapUrl");
    	venueAddress.setMobileNumber("molenumbe");
    	venueAddress.setPhoneNumber("ph nber");
    	venueAddress.setZipCode(12345L);
    	event.setVenueAddress(venueAddress);
    	event.setVenueName("venue name");
    	eventRepository.save(event);
    }
    

}
