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
}
