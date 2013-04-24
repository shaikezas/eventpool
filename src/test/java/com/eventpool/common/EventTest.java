package com.eventpool.common;

import javax.annotation.Resource;

import org.junit.Test;

import com.eventpool.common.entities.Event;
import com.eventpool.common.repositories.EventRepository;

public class EventTest extends BaseTest{
	
	@Resource
	EventRepository eventRepository;
	
	 
    @Test
    public void addEvent() {
    	Event event = new Event();
//    	event.setCategoryId(categoryId);
    }
    

}
