package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDTO;
import com.eventpool.common.entities.EventSettings;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.exceptions.TicketNotFoundException;
import com.eventpool.common.type.EventStatus;
import com.eventpool.event.command.PublishEventCommand;
import com.eventpool.event.command.SaveEventCommand;
import com.eventpool.event.module.EventApi;
import com.eventpool.event.service.EventCommandService;

/**
 
 */
@Service("EventService")
public class EventServiceImpl implements EventService {
    private static List<EventDTO> eventList = new ArrayList<EventDTO>();
    private static Long id = 0L;

    @Resource
    private EventApi eventApi;
    
	@Resource
	EventCommandService eventCommandService;
	
    
    public List<EventDTO> getAllEvents(Long userId) throws Exception {
        return eventApi.getAllEvents(userId);
    }

    public EventDTO getEventById(Long id) throws EventNotFoundException {
//        return findEventById(id);
    	return eventApi.getEvenDTO(id);
    }

    public boolean addEvent(EventDTO eventDTO) throws Exception {
    	SaveEventCommand saveEventCommand = new SaveEventCommand();
    	saveEventCommand.setEventDTO(eventDTO);
    	return eventCommandService.executeCommand(saveEventCommand);
    }

    public List<String> checkEventUrl(String eventUrl){
    	return eventApi.checkEventUrl(eventUrl);
    }
 
    
	public EventDTO getEventByUrl(String eventUrl) throws Exception {
		return eventApi.getEventByUrl(eventUrl);
	}
	
	public void updateEventSettings(EventSettings settings){
		eventApi.updateEventSettings(settings);
	}

	public boolean publishEvent(Long eventId, boolean isPublish) throws Exception {
		PublishEventCommand publishEventCommand = new PublishEventCommand();
		publishEventCommand.setEventId(eventId);
		publishEventCommand.setPublish(isPublish);
		return eventCommandService.executeCommand(publishEventCommand);
	}

	public boolean publishEvent(String eventUrl, boolean isPublish) throws Exception {
		PublishEventCommand publishEventCommand = new PublishEventCommand();
		publishEventCommand.setEventUrl(eventUrl);
		publishEventCommand.setPublish(isPublish);
		return eventCommandService.executeCommand(publishEventCommand);
	}

	public EventSettings getEventSettings(Long eventId) {
		return eventApi.getEventSettings(eventId);
	}

	public List<EventDTO> getAllEvents(EventStatus status) throws Exception {
		return eventApi.getAllEvents(status);
	}

	public List<EventDTO> getAllEvents(Long userId, EventStatus status) throws Exception {
		return eventApi.getAllEvents(userId,status);
	}

	public List<SuborderDTO> getOrderedTickets(Long userId) throws Exception {
		return eventApi.getOrderedTickets(userId);
	}

	public List<TicketInventoryDTO> getTicketInventoryDetails(Long eventId) {
		return eventApi.getTicketInventoryDetails(eventId);
	}

	public List<SuborderDTO> getEventOrderedTickets(Long eventId)
			throws Exception {
		return eventApi.getEventOrderedTickets(eventId);
	}

	public TicketDTO getTicketById(Long ticketId) throws TicketNotFoundException{
		
		return eventApi.getTicketById(ticketId);
	}

	public void updateEventClassification(Long eventId,
			Integer classificationType) throws Exception {
		eventApi.updateEventClassification(eventId, classificationType);
		
	}
}
