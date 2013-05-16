package com.eventpool.event.command;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.eventpool.common.commands.AbstractICommand;
import com.eventpool.common.commands.Command;
import com.eventpool.common.dto.EventDTO;


@SuppressWarnings("serial")
@Command
@XmlRootElement
public class SaveEventCommand extends AbstractICommand {
	
	@NotNull
	private EventDTO eventDTO;

	public EventDTO getEventDTO() {
		return eventDTO;
	}

	public void setEventDTO(EventDTO eventDTO) {
		this.eventDTO = eventDTO;
	}
	
}
