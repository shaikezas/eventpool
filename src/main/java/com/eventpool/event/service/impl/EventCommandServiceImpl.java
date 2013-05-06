package com.eventpool.event.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.eventpool.common.commands.ICommand;
import com.eventpool.event.service.EventCommandService;
import com.eventpool.ticket.command.impl.StandardGate;

@Component
public class EventCommandServiceImpl implements EventCommandService{


	@Resource
	StandardGate gate;

	
	public boolean executeCommand(ICommand command) throws Exception {
		gate.dispatch(command);
		return true;
	}

	
}
