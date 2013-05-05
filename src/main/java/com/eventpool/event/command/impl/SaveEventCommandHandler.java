package com.eventpool.event.command.impl;

import javax.annotation.Resource;

import com.eventpool.event.command.SaveEventCommand;
import com.eventpool.event.module.EventApi;
import com.eventpool.ticket.command.handler.CommandHandler;
import com.eventpool.ticket.command.handler.CommandHandlerAnnotation;



@CommandHandlerAnnotation
public class SaveEventCommandHandler implements CommandHandler<SaveEventCommand, Boolean>{
	
	@Resource
	EventApi eventApi;

	public Boolean handle(SaveEventCommand command)
			throws Exception {
		eventApi.saveEventDTO(command.getEventDTO());
		return true;
	}

}
