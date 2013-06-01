package com.eventpool.common.module;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventSettingsDTO;
import com.eventpool.common.dto.MediaDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.OrderFormSettings;
import com.eventpool.common.dto.RegistrationDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.Registration;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.entities.TicketSnapShot;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.TicketType;
import com.eventpool.web.forms.EventForm;
import com.eventpool.web.forms.TicketForm;

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
   	    myMappingFiles.add("dozer-mapping-files/eventFormMapping.xml");
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
		
		AddressDTO address = new AddressDTO();
		mapper.map(order.getBillingAddress(),address );
		orderDTO.setBillingAddress(address);
	}
	
	public void mapOrder(OrderDTO orderDTO,Order order){
		mapper.map(orderDTO,order);
		List<SuborderDTO> suborderDTOs = orderDTO.getSuborders();
		if(suborderDTOs!=null && suborderDTOs.size()>0){
			List<Suborder> suborders = new ArrayList<Suborder>();
			for(SuborderDTO suborderDTO:suborderDTOs){
				Suborder suborder = new Suborder();
				mapSuborder(suborderDTO,suborder);
				suborders.add(suborder);
			}
			order.setSuborders(suborders);
			
		}
		Address address = new Address();
		mapper.map(orderDTO.getBillingAddress(),address );
		order.setBillingAddress(address);
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
		
			TicketSnapShot ticketSnapShot = suborder.getTicketSnapShot();
			
			if(ticketSnapShot!=null){
				TicketDTO ticketDTO = new TicketDTO();
				mapper.map(ticketSnapShot, ticketDTO);
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
				TicketSnapShot ticketSnapShot = new TicketSnapShot();
				mapper.map(ticketDTO, ticketSnapShot);
				suborder.setTicketSnapShot(ticketSnapShot);
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
	
	public void map(RegistrationDTO registrationDTO,Registration registration){
		mapper.map(registrationDTO, registration);
	}
	
	public void map(Object source,Object destination){
		mapper.map(source, destination);
	}
	
	public void mapEventDTO(EventForm eventForm,EventDTO eventDTO){
		mapper.map(eventForm, eventDTO);
		
		AddressDTO venueAddress = eventDTO.getVenueAddress();
		if(venueAddress == null){
			venueAddress = new AddressDTO();
			eventDTO.setVenueAddress(venueAddress);
		}
		if(eventForm.getCityId()!=null){
			mapper.map(eventForm, venueAddress);
		}else{
			eventDTO.setVenueAddress(null);
		}
		
		
		MediaDTO media = eventDTO.getMedia();
		if(media == null){
			media = new MediaDTO();
			eventDTO.setMedia(media);
		}
		
		if(!checkIfNull(eventForm)){
			mapper.map(eventForm, media);
		}
		else{ 
			eventDTO.setMedia(null);
		}
		
		Set<TicketType> ticketTypes = new HashSet<TicketType>();
		List<TicketForm> ticketForms = eventForm.getTickets();
		if(ticketForms!=null && ticketForms.size()>0){
			List<TicketDTO> ticketDTOs = new ArrayList<TicketDTO>();
			for(TicketForm ticketForm:ticketForms){
				TicketDTO ticketDTO = new TicketDTO();
				mapper.map(ticketForm, ticketDTO);
				ticketTypes.add(TicketType.PAID);
				ticketDTOs.add(ticketDTO);
			}
			eventDTO.setTickets(ticketDTOs);
		}
		int type = 0;
		for(TicketType ticketType:ticketTypes){
			if(ticketType == TicketType.PAID){
				type = type | 1;
			}
			if(ticketType == TicketType.FREE){
				type = type | 2;
			}
			if(ticketType == TicketType.DONATION){
				type = type | 4;
			}

		}
		
		if(type == 1 ) {
			eventDTO.setEventType(EventType.PAID);
		}
		if(type == 2) {
			eventDTO.setEventType(EventType.FREE);
		}
		if(type == 3) {
			eventDTO.setEventType(EventType.PAIDNFREE);
		}
		if(type == 4){
			eventDTO.setEventType(EventType.NOREGISTRATION);
		}
		
		/*OrderFormSettings orderFormSettings = eventForm.getOrderFormSettings();
		if(orderFormSettings!=null){
			JSONObject json = new JSONObject(orderFormSettings);
			EventSettingsDTO eventSettingsDTO = eventDTO.getEventSettingsDTO();
			if(eventSettingsDTO==null){
				eventSettingsDTO = new EventSettingsDTO();
				eventDTO.setEventSettingsDTO(eventSettingsDTO);
			}
			eventSettingsDTO.setOrderFromSettings(json.toString());
		}*/
	}
	
	private boolean checkIfNull(EventForm eventForm) {
		if(eventForm.getOrganizerLogo() != null){
			return false;
		}
		if(eventForm.getBanner()!=null){
			return false;
		}
		if(eventForm.getVideoUrl()!=null){
			return false;
		}
		if(eventForm.getFaceBookUrl()!=null){
			return false;
		}
		if(eventForm.getOtherUrl1()!=null){
			return false;
		}
		if(eventForm.getOtherUrl2()!=null){
			return false;
		}
		if(eventForm.getEventUrl()!=null){
			return false;
		}
		if(eventForm.getEventWebSiteUrl()!=null){
			return false;
		}
		return true;
	}

	public void mapEventForm(EventDTO eventDTO,EventForm eventForm){
		mapper.map(eventDTO,eventForm);
		if(eventDTO.getVenueAddress()!=null){
			mapper.map(eventDTO.getVenueAddress(),eventForm);
		}
		if(eventDTO.getMedia()!=null){
			mapper.map(eventDTO.getMedia(), eventForm);
		}
		List<TicketDTO> ticketDTOs = eventDTO.getTickets();
		if(ticketDTOs!=null && ticketDTOs.size()>0){
			List<TicketForm> ticketForms = new ArrayList<TicketForm>();
			for(TicketDTO ticketDTO:ticketDTOs){
				TicketForm ticketForm = new TicketForm();
				mapper.map(ticketDTO,ticketForm);
				ticketForms.add(ticketForm);
			}
			eventForm.setTickets(ticketForms);
		}
/*		EventSettingsDTO eventSettingsDTO = eventDTO.getEventSettingsDTO();
		if(eventSettingsDTO!=null){
			String orderFromSettings = eventSettingsDTO.getOrderFromSettings();
			OrderFormSettings orderFormSettingObject =(OrderFormSettings) JSONObject.stringToValue(orderFromSettings);
			eventForm.setOrderFormSettings(orderFormSettingObject);
		}*/
	}

}
