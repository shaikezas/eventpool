package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventSettingsDTO;
import com.eventpool.common.dto.MediaDTO;
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
    
    static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Resource
    private EventApi eventApi;
    
	@Resource
	EventCommandService eventCommandService;
	
	@Produce(uri="jms:queue:event")
	ProducerTemplate eventQueue;
    
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
    	boolean executeCommand = eventCommandService.executeCommand(saveEventCommand);
    	if(executeCommand && eventDTO.getPublishDate()!=null && eventDTO.getId()!=null && eventDTO.getIsActive()!=null && eventDTO.getIsActive()){
    		pushToQueue(eventDTO.getId());
    	}
    	return executeCommand;
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
		boolean executeCommand = eventCommandService.executeCommand(publishEventCommand);
		try {
			if(executeCommand){
				pushToQueue(eventId);
			}
		} catch (Exception e) {
			logger.info("not able to push to active mq "+eventId,e);
		}
		return executeCommand;
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
	
	public void pushToQueue(Long eventId){
		eventQueue.sendBody(eventId);
	}

	
	public void closeEvent(Long eventId) throws Exception {
		EventDTO eventDTO = eventApi.getEvenDTO(eventId);
		eventDTO.setStatus(EventStatus.CLOSED);
		addEvent(eventDTO);
	}

	
	public Long copyEvent(Long eventId) throws Exception {
		EventDTO eventDTO = eventApi.getEvenDTO(eventId);
		eventDTO.setId(null);
		eventDTO.setStatus(EventStatus.DRAFT);
		eventDTO.setPublish(Boolean.FALSE);
		eventDTO.setTitle("Copy of "+ eventDTO.getTitle());
		eventDTO.setClassificationType(1);
		eventDTO.setPublishDate(null);
		eventDTO.setEventUrl(null);
		List<TicketDTO> tickets = eventDTO.getTickets();
		for(TicketDTO ticketDTO:tickets){
			ticketDTO.setId(null);
			ticketDTO.setEventId(null);
		}
		MediaDTO media = eventDTO.getMedia();
		media.setEventId(null);
		AddressDTO venueAddress = eventDTO.getVenueAddress();
		if(venueAddress!=null){
			venueAddress.setId(null);
		}
		EventSettingsDTO eventSettingsDTO = eventDTO.getEventSettingsDTO();
		if(eventSettingsDTO!=null){
			eventSettingsDTO.setId(null);
			eventSettingsDTO.setEventId(null);
		}
		addEvent(eventDTO);	
		return eventDTO.getId();
	}

	
	public void cancelEvent(Long eventId) throws Exception {
		EventDTO eventDTO = eventApi.getEvenDTO(eventId);
		eventDTO.setStatus(EventStatus.CANCELLED);
		addEvent(eventDTO);
	}
	
	
}
