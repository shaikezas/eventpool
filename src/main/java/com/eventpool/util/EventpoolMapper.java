package com.eventpool.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.RegistrationDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.Registration;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.Ticket;

@SuppressWarnings("rawtypes")
@Component
public class EventpoolMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(EventpoolMapper.class);
	
	DozerBeanMapper mapper;
	
	
	@SuppressWarnings("unchecked")
	public EventpoolMapper() {
		mapper = new DozerBeanMapper();
		List myMappingFiles = new ArrayList();
   	    myMappingFiles.add("dozer-mapping-files/orderMapping.xml");
   	 myMappingFiles.add("dozer-mapping-files/eventMapping.xml");
 	 	mapper.setMappingFiles(myMappingFiles);
	}
	
	@Transactional(readOnly=true)
	public void mapOrderDTO(Order order,OrderDTO orderDTO){
		mapper.map(order, orderDTO);
		List<Suborder> suborders = order.getSuborders();
		if(suborders!=null && suborders.size()>0){
			List<SuborderDTO> suborderDTOs = new ArrayList<SuborderDTO>();
			for(Suborder suborder:suborders){
				SuborderDTO suborderDTO = new SuborderDTO();
				mapper.map(suborder, suborderDTO);
				suborderDTOs.add(suborderDTO);
			}
			orderDTO.setSuborders(suborderDTOs);
		}
	}
	
	public void mapOrder(OrderDTO orderDTO,Order order){
		mapper.map(orderDTO,order);
		List<SuborderDTO> suborderDTOs = orderDTO.getSuborders();
		if(suborderDTOs!=null && suborderDTOs.size()>0){
			List<Suborder> suborders = new ArrayList<Suborder>();
			for(SuborderDTO suborderDTO:suborderDTOs){
				Suborder suborder = new Suborder();
				mapper.map(suborderDTO,suborder);
				suborders.add(suborder);
			}
			order.setSuborders(suborders);
			
		}
	}
	
	@Transactional(readOnly=true)
	public void mapSuborderDTO(Suborder suborder,SuborderDTO suborderDTO){
		mapper.map(suborder, suborderDTO);
		 List<Registration> registrations = suborder.getRegistrations();
		if(registrations!=null && registrations.size()>0){
			List<RegistrationDTO> registrationDTOs = new ArrayList<RegistrationDTO>();
			for(Registration registration:registrations){
				RegistrationDTO registrationDTO = new RegistrationDTO();
				mapper.map(registration, registrationDTO);
				registrationDTOs.add(registrationDTO);
			}
			suborderDTO.setRegistrations(registrationDTOs);
		}
		
			Ticket ticket = suborder.getTicket();
			
			if(ticket!=null){
				TicketDTO ticketDTO = new TicketDTO();
				mapper.map(ticket, ticketDTO);
				suborderDTO.setTicket(ticketDTO);
			}
			
			Order order = suborder.getOrder();
			if(order!=null){
				OrderDTO orderDTO = new OrderDTO();
				mapper.map(order,orderDTO);
				suborderDTO.setOrder(orderDTO);
			}
	}
	
	public void mapSuborder(SuborderDTO suborderDTO,Suborder suborder){
		mapper.map(suborderDTO, suborder);
		 List<RegistrationDTO> registrationDTOs = suborderDTO.getRegistrations();
		if(registrationDTOs!=null && registrationDTOs.size()>0){
			List<Registration> registrations = new ArrayList<Registration>();
			for(RegistrationDTO registrationDTO:registrationDTOs){
				Registration registration = new Registration();
				mapper.map(registrationDTO, registration);
				registrations.add(registration);
			}
			suborder.setRegistrations(registrations);
		}
		
			TicketDTO ticketDTO = suborderDTO.getTicket();
			
			if(ticketDTO!=null){
				Ticket ticket = new Ticket();
				mapper.map(ticketDTO, ticket);
				suborder.setTicket(ticket);
			}
			
			OrderDTO orderDTO = suborderDTO.getOrder();
			if(orderDTO!=null){
				Order order = new Order();
				mapper.map(orderDTO,order);
				suborder.setOrder(order);
			}
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