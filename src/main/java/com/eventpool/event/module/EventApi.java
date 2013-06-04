package com.eventpool.event.module;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDTO;
import com.eventpool.common.entities.EventSettings;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.type.EventStatus;

@Component
public interface EventApi {

    @Transactional
    public EventDTO saveEventDTO(EventDTO eventDTO);
    
    @Transactional(readOnly = true)
    public EventDTO getEvenDTO(Long id) throws EventNotFoundException;
    
    public List<String> checkEventUrl(String eventUrl);
    
    public List<EventDTO> getAllEvents(Long userId) throws Exception;
    
    public EventDTO getEventByUrl(String eventUrl) throws Exception;

	public void updateEventSettings(EventSettings settings);
	
	public EventSettings getEventSettings(Long eventId);
    
	public List<EventDTO> getAllEvents(EventStatus status) throws Exception;
    
	public List<EventDTO> getAllEvents(Long userId,EventStatus status) throws Exception;

	public List<SuborderDTO> getOrderedTickets(Long userId);
	 
	List<TicketInventoryDTO> getTicketInventoryDetails(Long eventId);
	
}
