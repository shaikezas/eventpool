package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.OrderStatusDTO;
import com.eventpool.common.dto.RegistrationDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.order.service.OrderService;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;
import com.google.gson.Gson;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderService orderService;
	
	  @RequestMapping(value = "/register", method = RequestMethod.POST)
	    public @ResponseBody OrderRegisterForm registerOrder(@RequestBody EventRegisterDTO eventRegister) throws NoTicketInventoryBlockedException {
		  OrderRegisterForm orderRegisterForm = null;  
		  try {
			  orderRegisterForm = orderService.registerOrder(eventRegister);
			} catch(NoTicketInventoryBlockedException e){
				throw e;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		  return orderRegisterForm;
	    }
	  
	  @RequestMapping(value = "/create", method = RequestMethod.POST)
	    public @ResponseBody OrderStatusDTO createOrder(@RequestBody OrderRegisterForm orderRegisterForm) {
		  OrderStatusDTO status = new OrderStatusDTO();
		  OrderDTO orderDTO = convertToOrderDTO(orderRegisterForm);
		  try {
			Order order = orderService.createOrder(orderDTO);
			if(order!=null && order.getId()!=null){
				status.setStatus("SUCCESS");
				status.setOrderId(order.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return status;
	    }
	  
	  private OrderDTO convertToOrderDTO(OrderRegisterForm orderRegisterForm){
		  OrderDTO orderDTO = new OrderDTO();
		  orderDTO.setBillingAddress(orderRegisterForm.getBillingAddress());
		  orderDTO.setCreatedBy(0L);
		  orderDTO.setCreatedDate(new Date());
		  orderDTO.setDicountCoupon(orderRegisterForm.getDicountCoupon());
		  orderDTO.setDiscountAmount(orderRegisterForm.getDiscountAmount());
		  orderDTO.setEmail(orderRegisterForm.getEmail());
		  orderDTO.setFirstName(orderRegisterForm.getFirstName());
		  orderDTO.setGrossAmount(orderRegisterForm.getGrossAmount());
		  orderDTO.setLastName(orderRegisterForm.getLastName());
		  orderDTO.setNetAmount(orderRegisterForm.getNetAmount());
		  orderDTO.setPaymentCurrency(orderRegisterForm.getPaymentCurrency());
		  
		  
		  Map<Long,SuborderDTO> suborderMap = new HashMap<Long,SuborderDTO>();
		  List<SuborderDTO> suborders = new ArrayList<SuborderDTO>();
		  orderDTO.setSuborders(suborders);
		  SuborderDTO suborderDTO = null;
		  
		  for(TicketRegisterDTO ticketRegisterDTO : orderRegisterForm.getTicketRegisters()){
			  
			  suborderDTO = new SuborderDTO();
			  suborderDTO.setCreatedBy(0L);
			  suborderDTO.setCreatedDate(new Date());
			  suborderDTO.setDicountCoupon(orderRegisterForm.getDicountCoupon());
			  suborderDTO.setDiscountAmount(orderRegisterForm.getDiscountAmount());
			  suborderDTO.setGrossAmount(ticketRegisterDTO.getQty() * ticketRegisterDTO.getPrice());
			  suborderDTO.setNetAmount(suborderDTO.getGrossAmount() - suborderDTO.getDiscountAmount());
			  suborderDTO.setOrder(orderDTO);
			  suborderDTO.setTicket(new TicketDTO());
			  suborderDTO.getTicket().setQuantity(ticketRegisterDTO.getQty());
			  suborderDTO.setStatus(OrderStatus.NEW);
			  suborderDTO.setSubCategoryId(orderRegisterForm.getSubCategoryId());
			  suborderDTO.setOrganizerName(orderRegisterForm.getOrganizerName());
			  suborderDTO.getTicket().setId(ticketRegisterDTO.getTicketId());
			  suborderDTO.setTicketPrice(ticketRegisterDTO.getPrice());
			  suborderDTO.setTicketRegisterId(ticketRegisterDTO.getId());
			  suborderDTO.getTicket().setName(ticketRegisterDTO.getTicketName());
			  suborders.add(suborderDTO);
			  suborderMap.put(suborderDTO.getTicket().getId(), suborderDTO);
			  
		  }
		  List<RegistrationDTO> registrations = null;
		  RegistrationDTO registrationDTO = null;
		  for(TicketRegisterForm regForm : orderRegisterForm.getTicketRegForms()){
			  suborderDTO = suborderMap.get(regForm.getTicketId());
			  registrationDTO = new RegistrationDTO();
			  
			  registrationDTO.setCreatedBy(0L);
			  registrationDTO.setCreatedDate(new Date());
			  Gson gson = new Gson();
			  registrationDTO.setAttendeeInfo(gson.toJson(regForm.getInfoSettings()));
			  if(suborderDTO.getRegistrations()==null){
				  registrations = new ArrayList<RegistrationDTO>();
				  suborderDTO.setRegistrations(registrations);
			  }
			  
			  suborderDTO.getRegistrations().add(registrationDTO);
			  
		  }
		  
		  
		  return orderDTO;
	  }
	  
	  
}
