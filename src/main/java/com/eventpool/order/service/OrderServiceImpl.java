package com.eventpool.order.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.exceptions.NoTicketInventoryAvailableException;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.common.module.DateCustomConverter;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.repositories.OrderRepository;
import com.eventpool.common.repositories.TicketRegisterRepository;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.event.service.impl.EventSettingsService;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.ticket.service.TicketInventoryUnblockedService;
import com.eventpool.web.controller.EventService;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;

@SuppressWarnings("rawtypes")
@Transactional(value = "transactionManager", propagation = REQUIRED)
@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private Validator validator;

	@Resource
	private EventpoolMapper eventpoolMapper;

	@Resource
	private OrderRepository orderRepository;

	@Resource
	TicketRegisterRepository ticketRegisterRepository;

	@Resource
	TicketInventoryUnblockedService unBlockedService;

	@Resource
	EventSettingsService infoService;

	@Autowired
	private EventService eventService;

	@Resource
	TicketInventoryService inventoryService;

	private static final Logger log = LoggerFactory
			.getLogger(OrderServiceImpl.class);

	@Resource
	private DateCustomConverter dateCustomConverter;

	public Order createOrder(OrderDTO orderDTO) throws Exception {
		Set<ConstraintViolation<OrderDTO>> validate = validator
				.validate(orderDTO);
		if (!CollectionUtils.isEmpty(validate)) {
			throw new ValidationException(validate.toString());
		}
		Order order = new Order();
		eventpoolMapper.mapOrder(orderDTO, order);

		order = orderRepository.save(order);

		for (SuborderDTO suborderDTO : orderDTO.getSuborders()) {
			createSubOrder(suborderDTO);
		}

		return order;

	}

	private void createSubOrder(SuborderDTO suborderDTO) throws Exception {

		TicketInventoryDetails updateTicketInventory = updateTicketInventory(suborderDTO);

		if (updateTicketInventory.isInvUpdated()) {
			ticketRegisterRepository.delete(suborderDTO.getTicketRegisterId());
		}
		// List<RegistrationDTO> registrationDTOs =
		// suborderDTO.getRegistrations();
		//
		// if(registrationDTOs!=null && registrationDTOs.size()>0){
		// for(RegistrationDTO registrationDTO:registrationDTOs){
		// createRegistration(suborder,registrationDTO);
		// }
		// }

	}

	private TicketInventoryDetails updateTicketInventory(SuborderDTO suborder)
			throws Exception {
		TicketOrderedCommand cmd = new TicketOrderedCommand();
		cmd.setQty(suborder.getTicket().getQuantity());
		cmd.setTicketId(suborder.getTicket().getId());
		TicketInventoryDetails inventoryDetails = (TicketInventoryDetails) inventoryService
				.executeCommand(cmd);
		return inventoryDetails;
	}

	public OrderRegisterForm registerOrder(EventRegisterDTO eventRegister)
			throws Exception {

		List<TicketRegister> ticketRegisters = blockTicketInventory(
				eventRegister.getTicketRegisterDTOs(),
				eventRegister.getRegistrationLimit());

		OrderRegisterForm orderRegisterForm = createOrderRegisterForm(
				ticketRegisters, eventRegister);

		return orderRegisterForm;
	}

	private OrderRegisterForm createOrderRegisterForm(
			List<TicketRegister> ticketRegisters, EventRegisterDTO eventRegister)
			throws EventNotFoundException {

		OrderRegisterForm orderRegisterForm = new OrderRegisterForm();
		EventDTO event = eventService.getEventById(eventRegister.getEventId());
		EventInfoType infoType = event.getInfoType();
		boolean isAttendeeRequired = false;
		Double grossAmount = 0.0D;
		orderRegisterForm.setDiscountAmount(eventRegister.getDiscountAmount());
		orderRegisterForm.setDicountCoupon(eventRegister.getDicountCoupon());
		orderRegisterForm
				.setPaymentCurrency(eventRegister.getPaymentCurrency());
		orderRegisterForm.setSubCategoryId(eventRegister.getSubCategoryId());
		if(!event.getIsWebinar()){
		orderRegisterForm.setVenueName(event.getVenueName());
		orderRegisterForm
				.setVenueAddress(event.getVenueAddress().getAddress1());
		}
		orderRegisterForm.setStartDate(dateCustomConverter.convertFrom(event
				.getStartDate()));
		orderRegisterForm.setEndDate(dateCustomConverter.convertFrom(event
				.getEndDate()));

		if (!infoType.equals(EventInfoType.BASIC)) {
			isAttendeeRequired = true;
		}
		List<TicketRegisterDTO> ticketRegisterDTOs = new ArrayList<TicketRegisterDTO>();
		List<TicketRegisterForm> ticketRegForms = new ArrayList<TicketRegisterForm>();
		orderRegisterForm.setTicketRegForms(ticketRegForms);
		orderRegisterForm.setTicketRegisters(ticketRegisterDTOs);
		TicketRegisterDTO ticketRegisterDTO = null;
		TicketRegisterForm ticketRegisterForm = null;
		int totalTickets = 0;
		for (TicketRegister ticketRegister : ticketRegisters) {
			ticketRegisterDTO = new TicketRegisterDTO();
			eventpoolMapper.map(ticketRegister, ticketRegisterDTO);
			ticketRegisterDTOs.add(ticketRegisterDTO);
			totalTickets += ticketRegisterDTO.getQty();
			if (isAttendeeRequired) {
				for(int i =0;i<ticketRegister.getQty();i++){
				ticketRegisterForm = new TicketRegisterForm();
				ticketRegisterForm.setTicketId(ticketRegisterDTO.getTicketId());
				ticketRegisterForm.setTicketName(ticketRegisterDTO
						.getTicketName());
				ticketRegisterForm.setInfoSettings(infoService
						.getEventInfoSettings(event));
				ticketRegForms.add(ticketRegisterForm);
				}
			}
			grossAmount = grossAmount + ticketRegisterDTO.getQty()
					* ticketRegisterDTO.getPrice();
		}
		orderRegisterForm.setTotalTickets(totalTickets);
		orderRegisterForm.setNetAmount(grossAmount
				- orderRegisterForm.getDiscountAmount());
		orderRegisterForm.setGrossAmount(grossAmount);
		return orderRegisterForm;
	}

	private List<TicketRegister> blockTicketInventory(
			List<TicketRegisterDTO> ticketRegisterDTOs, int registrationLimit)
			throws Exception {
		TicketBlockedCommand blockcmd = new TicketBlockedCommand();
		blockcmd.setBlockingQty(1);
		blockcmd.setTicketId(101L);
		TicketInventoryDetails ticketInventoryDetails = null;
		List<TicketRegister> ticketRegisters = new ArrayList<TicketRegister>();
		TicketRegister ticketRegister = null;
		for (TicketRegisterDTO ticketRegisterDTO : ticketRegisterDTOs) {
			ticketRegister = new TicketRegister();
			eventpoolMapper.map(ticketRegisterDTO, ticketRegister);
			blockcmd.setBlockingQty(ticketRegister.getQty());
			blockcmd.setTicketId(ticketRegister.getTicketId());
			ticketInventoryDetails = (TicketInventoryDetails) inventoryService
					.executeCommand(blockcmd);
			if (ticketInventoryDetails == null) {
				throw new NoTicketInventoryAvailableException("NO_INVENTORY");
			}
			if (ticketInventoryDetails.isInvBlocked()) {
				ticketRegister.setQty(ticketInventoryDetails.getBlockingQty());
				ticketRegister = ticketRegisterRepository.save(ticketRegister);
				unBlockedService.registerTask(ticketRegister,
						registrationLimit * 60);
			} else {
				throw new NoTicketInventoryBlockedException(
						"Unable to block Ticket : sellable quantity is "
								+ ticketInventoryDetails.getSellableQty());
			}
			ticketRegisters.add(ticketRegister);
		}
		return ticketRegisters;
	}

}
