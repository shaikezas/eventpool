package com.eventpool.event.command;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.eventpool.common.commands.AbstractICommand;
import com.eventpool.common.commands.Command;
import com.eventpool.common.dto.EventDTO;


@SuppressWarnings("serial")
@Command
@XmlRootElement
public class PublishEventCommand extends AbstractICommand {
	
	private Long  eventId;
	private boolean publish;
	private String eventUrl;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	
	
}
