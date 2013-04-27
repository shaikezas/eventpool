package com.eventpool.event.module;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Ticket;

@SuppressWarnings("rawtypes")
@Component
public class EventMapper {

    private static final Logger logger = LoggerFactory.getLogger(EventMapper.class);
	
	DozerBeanMapper mapper;
	
	
	
	@SuppressWarnings("unchecked")
	public EventMapper() {
		mapper = new DozerBeanMapper();
		List myMappingFiles = new ArrayList();
   	    myMappingFiles.add("dozer-mapping-files/eventMapping.xml");
 	 	mapper.setMappingFiles(myMappingFiles);
	}
	
	@Transactional(readOnly=true)
	public void mapEventDTO(Event event,EventDTO eventDTO){
		mapper.map(event, eventDTO);
		List<Ticket> tickets = event.getTickets();
		if(tickets!=null && tickets.size()>0){
			List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
			for(Ticket ticket:tickets){
				TicketDTO ticketDTO = new TicketDTO();
				mapper.map(ticket, ticketDTO);
				ticketDTOs.add(ticketDTO);
			}
			eventDTO.setTickets(ticketDTOs);
		}
	}
	
	@Transactional()
	public void mapEvent(EventDTO eventDTO,Event event){
		mapper.map(eventDTO,event);
		List<TicketDTO> ticketDTOs = eventDTO.getTickets();
		if(ticketDTOs!=null && ticketDTOs.size()>0){
			List<Ticket> tickets = event.getTickets();
			for(TicketDTO ticketDTO:ticketDTOs){
				boolean found = false;
				if(tickets!=null && tickets.size()>0){
					for(Ticket ticket:tickets){
						if(ticket.getId()!=null && ticket.getId().compareTo(ticketDTO.getId())==0){
							mapper.map(ticketDTO, ticket);
							found = true;
						}
					}
				}else{
					tickets = new ArrayList<Ticket>();
					event.setTickets(tickets);
				}
				if(!found){
					Ticket ticket = new Ticket();
					mapper.map(ticketDTO, ticket);
					tickets.add(ticket);
				}
			}
		}
	}
}
