package com.eventpool.event.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.EventSettings;
import com.eventpool.common.entities.MemberShip;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.entities.TicketInventory;
import com.eventpool.common.entities.User;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.exceptions.TicketNotFoundException;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.repositories.EventMediaRepository;
import com.eventpool.common.repositories.EventRepository;
import com.eventpool.common.repositories.EventSettingsRepository;
import com.eventpool.common.repositories.MemberShipRepository;
import com.eventpool.common.repositories.SuborderRepository;
import com.eventpool.common.repositories.TicketInventoryRepository;
import com.eventpool.common.repositories.TicketRepository;
import com.eventpool.common.repositories.UserRepository;
import com.eventpool.common.type.EventStatus;


@Component
public class EventApiImpl implements EventApi{

    private static final Logger logger = LoggerFactory.getLogger(EventApiImpl.class);
	
    @Resource
    private EventpoolMapper eventpoolMapper;
    
    @Resource
    private EventRepository eventRepository;

    @Resource
    private SuborderRepository suborderRepository;
   
    @Resource
    private EventMediaRepository eventMediaRepository;
    
    @Resource
    private EventSettingsRepository eventSettingsRepository;
    
    @Resource
	TicketInventoryRepository ticketInventoryRepository;
    
    @Resource
    TicketRepository ticketRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    MemberShipRepository memberShipRepository;

    @Transactional(rollbackFor=RuntimeException.class)
    public EventDTO saveEventDTO(EventDTO eventDTO){
    	if(eventDTO==null) throw new IllegalArgumentException("Input event DTO is null");
    	Long id = eventDTO.getId();
    	Event event = null;
    	if(id == null){
    		event = new Event();
    	}else{
    		event = eventRepository.findOne(id);
    	}
    	eventpoolMapper.mapEvent(eventDTO, event);
    	if(event.getSubCategoryId()  == null){
    		event.setSubCategoryId(1000);
    	}
    	setDefaultValues(event);
    	eventRepository.save(event);
    	eventpoolMapper.mapEventDTO(event, eventDTO);
    	logger.info("event saved before commit {}",event.getId());
    	return eventDTO;
    }
    
	private void setDefaultValues(Event event) {
		if(event.getStatus()==null){
			event.setStatus(EventStatus.DRAFT);
		}
	}

	@Transactional(readOnly = true)
    public EventDTO getEvenDTO(Long id) throws EventNotFoundException{
    	if(id == null) throw new IllegalArgumentException("Input Event id is null");
    	Event event = eventRepository.findOne(id);
    	if(event == null) throw new EventNotFoundException();
    	EventDTO eventDTO = new EventDTO();
    	eventpoolMapper.mapEventDTO(event, eventDTO);
    	return eventDTO;
    }

	public List<String> checkEventUrl(String eventUrl) {
		Event event = eventRepository.findEventUrl(eventUrl);
		//getSuggestion(eventUrl);
		if(event == null){
			List<String> suggestions = new ArrayList<String>();
			suggestions.add(eventUrl);
		}
		return new ArrayList<String>();
	}

	private Set<String> getSuggestion(String eventUrl) {
		Set<String> suggestionUrls = new HashSet<String>();
		int i=1;
		for(i=1;i<10;i++){
			suggestionUrls.add(eventUrl+""+i);
		}
		return suggestionUrls;
	}

	@Transactional(readOnly = true)
	public List<EventDTO> getAllEvents(Long userId) throws Exception {
		List<Event> allEvents = eventRepository.getAllEvents(userId);
		List<EventDTO> eventDtoList = new ArrayList<EventDTO>();
		if(allEvents!=null && allEvents.size()>0){
			for(Event event:allEvents){
				EventDTO eventDTO = new EventDTO();
				eventpoolMapper.mapEventDTO(event, eventDTO);
				eventDtoList.add(eventDTO);
			}
		}
		return eventDtoList;
	}

	@Transactional(readOnly = true)
	public EventDTO getEventByUrl(String eventUrl) throws Exception {
		Event event = eventRepository.getByEventUrl(eventUrl);
		EventDTO eventDTO = new EventDTO();
		eventpoolMapper.mapEventDTO(event, eventDTO);
		return eventDTO;
	}

	public void updateEventSettings(EventSettings settings) {
		eventSettingsRepository.save(settings);
		
	}

	public EventSettings getEventSettings(Long eventId) {
		return eventSettingsRepository.findOne(eventId);
		
	}

	@Transactional(readOnly = true)
	public List<EventDTO> getAllEvents(EventStatus status) throws Exception {
		List<Event> allEvents = eventRepository.getAllEvents(status);
		List<EventDTO> eventDtoList = new ArrayList<EventDTO>();
		if(allEvents!=null && allEvents.size()>0){
			for(Event event:allEvents){
				EventDTO eventDTO = new EventDTO();
				eventpoolMapper.mapEventDTO(event, eventDTO);
				eventDtoList.add(eventDTO);
			}
		}
		return eventDtoList;
	}

	@Transactional(readOnly = true)
	public List<EventDTO> getAllEvents(Long userId, EventStatus status)
			throws Exception {
		List<Event> allEvents = eventRepository.getAllEvents(userId,status);
		List<EventDTO> eventDtoList = new ArrayList<EventDTO>();
		if(allEvents!=null && allEvents.size()>0){
			for(Event event:allEvents){
				EventDTO eventDTO = new EventDTO();
				eventpoolMapper.mapEventDTO(event, eventDTO);
				eventDtoList.add(eventDTO);
			}
		}
		return eventDtoList;
	}

	@Transactional(readOnly = true)
	public List<SuborderDTO> getOrderedTickets(Long userId) {
		List<Suborder> suborders = suborderRepository.getSuborders(userId);
		return mapSuborderDTO(suborders);
	}

	@Transactional(readOnly = true)
	private List<SuborderDTO> mapSuborderDTO(List<Suborder> suborders) {
		List<SuborderDTO> suborderDTOs = new ArrayList<SuborderDTO>();
		if(suborders!=null && suborders.size()>0){
			for(Suborder suborder:suborders){
				SuborderDTO suborderDTO = new SuborderDTO();
				eventpoolMapper.mapSuborderDTO(suborder, suborderDTO);
				suborderDTOs.add(suborderDTO);
			}
		}
		return suborderDTOs;
	}

	public List<TicketInventoryDTO> getTicketInventoryDetails(Long eventId) {
		logger.info("getting ticket inventory for an event {}",eventId);
		
		//TODO: need to reduce two calls.
		List<TicketInventory> ticketInventorys = ticketInventoryRepository.findTicketInventoryByEventId(eventId);
		List<Ticket> eventTickets = eventRepository.getEventTickets(eventId);
		
		List<TicketInventoryDTO> tiketInventoryDTOS = new ArrayList<TicketInventoryDTO>();
		if(eventTickets!=null && eventTickets.size()>0){
			for(Ticket ticket:eventTickets){
				boolean found = false;
				if(ticketInventorys!=null && ticketInventorys.size()>0){
					for(TicketInventory ticketInventory:ticketInventorys){
						if(ticket.getId().compareTo(ticketInventory.getTicketId())==0){
							Double price = ticket.getPrice();
							found = true;
							TicketInventoryDTO ticketInventoryDTO = new TicketInventoryDTO();
							eventpoolMapper.map(ticketInventory, ticketInventoryDTO);
							ticketInventoryDTO.setPrice(price);
							tiketInventoryDTOS.add(ticketInventoryDTO);
							

						}
					}
					if(!found){
						TicketInventoryDTO ticketInventoryDTO = new TicketInventoryDTO();
						ticketInventoryDTO.setMaxQty(ticket.getMaxQty());
						Double price = ticket.getPrice();
						ticketInventoryDTO.setPrice(price);
						ticketInventoryDTO.setTicketId(ticket.getId());
						ticketInventoryDTO.setQty(ticket.getQuantity());
						ticketInventoryDTO.setBlockingQty(0);
						ticketInventoryDTO.setMaxQty(ticket.getMaxQty());
						ticketInventoryDTO.setSoldQty(0);
						tiketInventoryDTOS.add(ticketInventoryDTO);
					}
				}
			}
		}
		
		return tiketInventoryDTOS;
	}

	@Transactional(readOnly=true)
	public List<SuborderDTO> getEventOrderedTickets(Long eventId) {
		List<Suborder> suborders = suborderRepository.getEventSuborders(eventId);
		return mapSuborderDTO(suborders);
	}

	public TicketDTO getTicketById(Long ticketId) throws TicketNotFoundException {
		
		if(ticketId == null) throw new IllegalArgumentException("Input Ticket id is null");
    	 Ticket ticket = ticketRepository.findOne(ticketId);
    	if(ticket == null) throw new TicketNotFoundException();
    	TicketDTO ticketDTO = new TicketDTO();
    	eventpoolMapper.mapTicketDTO(ticket, ticketDTO);
    	return ticketDTO;
	}

	@Transactional
	public void updateEventClassification(Long eventId,Integer classificationType) throws Exception {
		Event event = eventRepository.findOne(eventId);
		Long userId = event.getCreatedBy();
		User user = userRepository.findOne(userId);
		Integer totalPoints = user.getTotalPoints();
		MemberShip classification = memberShipRepository.findOne(classificationType);
		Integer pointsPerEvent = classification.getPointsPerEvent();
		if((pointsPerEvent>totalPoints || user.getMemberShipExp().compareTo(new Date())>=0) && user.getMemberShip().getId()<=classificationType){
			throw new Exception("Event can't be calssified, point are less or membership expired");
		}else{
			event.setClassificationType(classificationType);
			eventRepository.save(event);
			user.setTotalPoints(totalPoints-pointsPerEvent);
			userRepository.save(user);
		}
	}

}
