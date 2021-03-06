package com.eventpool.order.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

import com.eventpool.common.dto.AddressDTO;
import com.eventpool.common.dto.EventDTO;
import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.Region;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.entities.Address;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.entities.TicketSnapShot;
import com.eventpool.common.entities.User;
import com.eventpool.common.exceptions.EventNotFoundException;
import com.eventpool.common.exceptions.NoTicketInventoryAvailableException;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.common.exceptions.TicketNotFoundException;
import com.eventpool.common.module.DateCustomConverter;
import com.eventpool.common.module.EntityUtilities;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.repositories.OrderRepository;
import com.eventpool.common.repositories.TicketRegisterRepository;
import com.eventpool.common.repositories.UserRepository;
import com.eventpool.common.type.CurrencyType;
import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventType;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.common.type.PaymentStatus;
import com.eventpool.event.service.impl.EventSettingsService;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.ticket.service.TicketInventoryUnblockedService;
import com.eventpool.web.controller.EventService;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;
import com.google.common.util.concurrent.DaemonThreadFactory;

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
	
	@Autowired
	InvoiceService invoiceService;
	
	@Resource
	TicketInventoryService inventoryService;
	
	@Resource
    private EntityUtilities  entityUtilities;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private UserService userService;

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
		for (SuborderDTO suborderDTO : orderDTO.getSuborders()) {
			suborderDTO.setStatus(OrderStatus.INITIATED);
			updateTicketDTO(suborderDTO);
		}
		eventpoolMapper.mapOrder(orderDTO, order);
		order.setStatus(OrderStatus.INITIATED);
		order.setPaymentStatus(PaymentStatus.WAITING_FOR_GATEWAYRESPONSE);
		order = orderRepository.save(order);
		return order;

	}
	
	public void updateToken(Long orderId,String token) {
		Order order = orderRepository.findOne(orderId);
		order.setToken(token);
		orderRepository.save(order);
	}

	public OrderDTO postOrder(Long orderId,String token,String payerId) throws Exception{
		Date start = new Date();
		Order order = orderRepository.findOne(orderId);
		Boolean validTicketRegister = Boolean.TRUE;
		Double grossAmount = order.getGrossAmount();
		EventType eventType = null;
		Integer qty = 0;
		if((order.getToken()!=null && order.getToken().equals(token)) || (grossAmount!=null && grossAmount.compareTo(0.0)==0)){
			for (Suborder suborder : order.getSuborders()) {
				SuborderDTO suborderDTO = new SuborderDTO();
				eventpoolMapper.mapSuborderDTO(suborder, suborderDTO);
				validTicketRegister = validateTicketRegister(suborderDTO);
				TicketSnapShot ticketSnapShot = suborder.getTicketSnapShot();
				eventType = ticketSnapShot.getEvent().getEventType();
				qty+=ticketSnapShot.getQuantity();
				if(!validTicketRegister){
					break;
				}
			}
				
			if(validTicketRegister){
				for (Suborder suborder : order.getSuborders()) {
					SuborderDTO suborderDTO = new SuborderDTO();
					eventpoolMapper.mapSuborderDTO(suborder, suborderDTO);
					deleteTicketRegister(suborderDTO);
				}
			
				for(Suborder suborder :  order.getSuborders()){
					suborder.setStatus(OrderStatus.SUCCESS);
					invoiceService.generateInvoice(suborder);
				}
				order.setStatus(OrderStatus.SUCCESS);
				order.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
				orderRepository.save(order);
				if(eventType!=null && eventType.compareTo(EventType.PACKAGE)==0){
					Long userId = userService.getCurrentUser().getId();
				 	User user = userRepository.findOne(userId);
				 	if(qty!=null){
				    	user.setTotalPoints(user.getTotalPoints()+qty);
				    	userRepository.save(user);
				 	}
				}
				 OrderDTO orderDTO = getOrderDTO(order);
				Date end = new Date();
				log.info(" Time taken for order processing"+(end.getTime()-start.getTime()));
				return orderDTO;
			}
		}else{
			releaseInventory(orderId);
			order.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
			orderRepository.save(order);
		}
		return null;
	}
	
	private void updateTicketDTO(SuborderDTO suborderDTO) throws TicketNotFoundException{
		 Long ticketId = suborderDTO.getTicket().getId();
		 TicketDTO ticketDTO = eventService.getTicketById(ticketId);
		
		if(ticketDTO!=null){
			suborderDTO.getTicket().setSaleStart(ticketDTO.getSaleStart());
			suborderDTO.getTicket().setSaleEnd(ticketDTO.getSaleEnd());
			suborderDTO.getTicket().setEventId(ticketDTO.getEventId());
			suborderDTO.getTicket().setMinQty(ticketDTO.getMinQty());
			suborderDTO.getTicket().setMaxQty(ticketDTO.getMaxQty());
			suborderDTO.getTicket().setIsActive(ticketDTO.getIsActive());
			suborderDTO.getTicket().setTicketOrder(ticketDTO.getTicketOrder());
			suborderDTO.getTicket().setTicketType(ticketDTO.getTicketType());
			
		}
	}
	private void deleteTicketRegister(SuborderDTO suborderDTO) throws Exception {

		TicketInventoryDetails updateTicketInventory = updateTicketInventory(suborderDTO);

		if (updateTicketInventory.isInvUpdated()) {
			if(suborderDTO.getTicketRegisterId()!=null)
			ticketRegisterRepository.delete(suborderDTO.getTicketRegisterId());
		}

	}
	
	private Boolean validateTicketRegister(SuborderDTO suborderDTO) throws Exception {

		TicketRegister tr = ticketRegisterRepository.findOne(suborderDTO.getTicketRegisterId());

		if(tr!=null){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
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
		orderRegisterForm.setRegistrationLimit(eventRegister.getRegistrationLimit()*60);
		orderRegisterForm.setStartTime(new Date());
		return orderRegisterForm;
	}

	private OrderRegisterForm createOrderRegisterForm(
			List<TicketRegister> ticketRegisters, EventRegisterDTO eventRegister)
			throws EventNotFoundException {
		
		OrderRegisterForm orderRegisterForm = new OrderRegisterForm();
		User user = userService.getCurrentUser();
		orderRegisterForm.setFirstName(user.getFname());
		orderRegisterForm.setLastName(user.getLname());
		orderRegisterForm.setEmail(user.getEmail());
		EventDTO event = eventService.getEventById(eventRegister.getEventId());
		
/*		//TODO: this code need to be improved by using User address.
		List<Order> orders = orderRepository.getAllOrders(user.getId());
		Order order = null;
		if(orders!=null && orders.size()>0){
			order = orders.get(0);
		}
*/		Address billingAddress = user.getOfficeAddress();
		
		if(billingAddress!=null){
			//Address billingAddress = order.getBillingAddress();
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setAddress1(billingAddress.getAddress1());
			addressDTO.setAddress2(billingAddress.getAddress2());
			addressDTO.setCityId(billingAddress.getCityId());
			addressDTO.setMobileNumber(billingAddress.getMobileNumber());
			addressDTO.setPhoneNumber(billingAddress.getPhoneNumber());
			addressDTO.setZipCode(billingAddress.getZipCode());
			if(addressDTO.getCityId()!=null){
		    	Map<Integer, Region> csc = entityUtilities.getCitiesWithStateAndCountry();
		    	Region region = csc.get(addressDTO.getCityId());
		    	addressDTO.setCityName(region.getCityName());
		    	addressDTO.setStateName(region.getStateName());
		    	addressDTO.setCountryName(region.getCountryName());
		    	}
			orderRegisterForm.setBillingAddress(addressDTO);
		}
		
		EventInfoType infoType = event.getInfoType();
		boolean isAttendeeRequired = false;
		Double grossAmount = 0.0D;
		orderRegisterForm.setDiscountAmount(eventRegister.getDiscountAmount());
		orderRegisterForm.setDicountCoupon(eventRegister.getDicountCoupon());
		orderRegisterForm
				.setPaymentCurrency(CurrencyType.fromValue(event.getCurrency()));
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
		orderRegisterForm.setEventName(event.getTitle());
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
				ticketRegister.setRegistrationLimit(registrationLimit);
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

	@Override
	public OrderDTO getOrderDTO(Long orderId) {
		Order order = orderRepository.findOne(orderId);
		return getOrderDTO(order);
	}

	private OrderDTO getOrderDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		eventpoolMapper.mapOrderDTO(order, orderDTO );
		return orderDTO;
	}

	@Override
	public void releaseInventory(Long orderId) {
		Order order = orderRepository.findOne(orderId);
		order.setStatus(OrderStatus.FAILED);
		for(Suborder suborder:order.getSuborders()){
			suborder.setStatus(OrderStatus.FAILED);
			TicketRegister ticketRegister = null;
			if(suborder.getTicketRegisterId()!=null) {
				ticketRegister = ticketRegisterRepository.findOne(suborder.getTicketRegisterId());
			}
			if(ticketRegister==null){
				continue;
			}
		TicketUnBlockedCommand unblockcmd  = new TicketUnBlockedCommand();
	      unblockcmd.setUnBlockingQty(ticketRegister.getQty());
	      unblockcmd.setTicketId(ticketRegister.getTicketId());
	      try {
	    	  TicketRegister register = ticketRegisterRepository.findOne(ticketRegister.getId());
	    	  if(register!=null){
	    		  TicketInventoryDetails ticketInventoryDetails = (TicketInventoryDetails) inventoryService.executeCommand(unblockcmd);
	    		  if(ticketInventoryDetails.isInvUnBlocked()){
	    			  ticketRegisterRepository.delete(ticketRegister.getId());
	    		  }else{
	    			  log.error("Unable to unblock the ticket inventory for ticket register id "+ticketRegister.getId());
	    		  }
			}else{
				log.error("Order placed for the ticket register id "+ticketRegister.getId());
			}
			
		} catch (Exception e) {
			log.error("Exception in UnblockTask run method",e);		
		}
		}
		orderRepository.save(order);
	}

}
