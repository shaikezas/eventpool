package com.eventpool.event.service;

import org.springframework.stereotype.Component;

import com.eventpool.common.commands.ICommand;

@Component
public interface EventCommandService {

	public boolean executeCommand(ICommand command) throws Exception;
}
