package com.eventpool.event.command.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eventpool.common.repositories.EventRepository;
import com.eventpool.event.command.PublishEventCommand;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;



@CommandHandlerAnnotation
public class PublishEventCommandHandler implements CommandHandler<PublishEventCommand, Boolean>{
	
	@Resource
	EventRepository eventRepository;
	

	private static final Logger logger = LoggerFactory.getLogger(PublishEventCommandHandler.class);
	
	
	public Boolean handle(PublishEventCommand command)
			throws Exception {
		Long eventId = command.getEventId();
		String eventUrl = command.getEventUrl();
		boolean isPublish = command.isPublish();

		if(eventId!=null){
			eventRepository.publish(eventId, isPublish);
		}
		else if(eventUrl!=null){
			eventRepository.publish(eventUrl, isPublish);
		}
		else{
			logger.error("Invalid inputs");
			throw new IllegalArgumentException("invalid arguments");
		}
		return true;
	}

}
