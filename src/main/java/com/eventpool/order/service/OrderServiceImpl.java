package com.eventpool.order.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.RegistrationDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.Registration;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.exceptions.NoTicketInventoryAvailableException;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.common.repositories.OrderRepository;
import com.eventpool.common.repositories.RegistrationRepository;
import com.eventpool.common.repositories.SuborderRepository;
import com.eventpool.common.repositories.TicketRegisterRepository;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.ticket.service.TicketInventoryUnblockedService;
import com.eventpool.util.EventpoolMapper;
import com.eventpool.web.forms.OrderRegisterForm;

@SuppressWarnings("rawtypes")
@Transactional(value = "transactionManager", propagation = REQUIRED)
@Service
public class OrderServiceImpl implements OrderService{

	 @Resource
	 private Validator validator;
	 
	 @Resource
     private EventpoolMapper eventpoolMapper;
	 
	 @Resource
	 private OrderRepository orderRepository;
	 
	 @Resource
	 private SuborderRepository suborderRepository;
	 
	 @Resource
	 TicketRegisterRepository ticketRegisterRepository;
	 
	 @Resource
		TicketInventoryUnblockedService unBlockedService;
	 
	 DozerBeanMapper mapper;
	 
	 @Resource
	 TicketInventoryService inventoryService;
	 
	 private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	 
	 @SuppressWarnings("unchecked")
	 public OrderServiceImpl(){
		 mapper = new DozerBeanMapper();
			List myMappingFiles = new ArrayList();
	   	    myMappingFiles.add("dozer-mapping-files/orderMapping.xml");
	   	 myMappingFiles.add("dozer-mapping-files/eventMapping.xml");
	 	 	mapper.setMappingFiles(myMappingFiles);
	 }
	 @Resource
	 private RegistrationRepository registrationRepository;
	public void creteOrder(OrderDTO orderDTO) throws Exception {
		Set<ConstraintViolation<OrderDTO>> validate = validator.validate(orderDTO);
        if (!CollectionUtils.isEmpty(validate)) {
            throw new ValidationException(validate.toString());
        }
        Order order = new Order();
        eventpoolMapper.mapOrder(orderDTO, order);

        order = orderRepository.save(order);
        
        for (SuborderDTO suborderDTO : orderDTO.getSuborders()) {
            createSubOrder(suborderDTO);
        }
		
	}

	private void createSubOrder( SuborderDTO suborderDTO) throws Exception {
		
		Suborder suborder = new Suborder();
		
		eventpoolMapper.mapSuborder(suborderDTO, suborder);
		
		suborder  = suborderRepository.save(suborder);
		updateTicketInventory(suborder);
		List<RegistrationDTO> registrationDTOs = suborderDTO.getRegistrations();
		
		if(registrationDTOs!=null && registrationDTOs.size()>0){
			for(RegistrationDTO registrationDTO:registrationDTOs){
				createRegistration(suborder,registrationDTO);
			}
		}
		
	}

	private void updateTicketInventory(Suborder suborder) throws Exception {
		TicketOrderedCommand cmd = new TicketOrderedCommand();
		cmd.setQty(suborder.getQuantity());
		cmd.setTicketId(suborder.getTicket().getId());
		inventoryService.executeCommand(cmd);
	}

	private void createRegistration(Suborder suborder,RegistrationDTO registrationDTO) {
			
			Registration registration = new Registration();
			mapper.map(registrationDTO, registration);
			registration.setSuborderId(suborder.getId());
			registrationRepository.save(registration);
	}

	public OrderRegisterForm registerOrder(List<TicketRegisterDTO> ticketRegisterDTOs) throws Exception {
		TicketBlockedCommand blockcmd = new TicketBlockedCommand();
		blockcmd.setBlockingQty(1);
		blockcmd.setTicketId(101L);
		List<TicketRegister> ticketRegisters = new ArrayList<TicketRegister>();
		
		TicketInventoryDetails ticketInventoryDetails=null ;
		TicketRegister ticketRegister = null;
		for(TicketRegisterDTO ticketRegisterDTO : ticketRegisterDTOs){
			ticketRegister = new TicketRegister();
			mapper.map(ticketRegisterDTO, ticketRegister);
			blockcmd.setBlockingQty(ticketRegister.getQty());
			blockcmd.setTicketId(ticketRegister.getTicketId());
		    ticketInventoryDetails = (TicketInventoryDetails) inventoryService.executeCommand(blockcmd);
		    if(ticketInventoryDetails==null){
		    		throw new NoTicketInventoryAvailableException("NO_INVENTORY");
		    }
		    if(ticketInventoryDetails.isInvBlocked()){
		    	ticketRegister.setQty(ticketInventoryDetails.getBlockingQty());
		    	ticketRegister = ticketRegisterRepository.save(ticketRegister);
		    	unBlockedService.registerTask(ticketRegister, 30);
		    }else{
		    	throw new NoTicketInventoryBlockedException("Unable to block Ticket");
		    }
		    ticketRegisters.add(ticketRegister);
		}
		return null;
	}

}
