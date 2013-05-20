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
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.order.service.OrderService;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Resource
	OrderService orderService;
	
	  @RequestMapping(value = "/register", method = RequestMethod.POST)
	    public @ResponseBody OrderRegisterForm registerOrder(@RequestBody EventRegisterDTO eventRegister) {
		  OrderRegisterForm orderRegisterForm = null;  
		  try {
			  orderRegisterForm = orderService.registerOrder(eventRegister);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  return orderRegisterForm;
	    }
	  
	  @RequestMapping("/create")
	    public @ResponseBody OrderStatusDTO createOrder(OrderRegisterForm orderRegisterForm) {
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
			  suborderDTO.setQuantity(ticketRegisterDTO.getQty());
			  suborderDTO.setStatus(OrderStatus.NEW);
			  suborderDTO.setSubCategoryId(orderRegisterForm.getSubCategoryId());
			  suborderDTO.setOrganizerName(orderRegisterForm.getOrganizerName());
			  suborderDTO.setTicketId(ticketRegisterDTO.getTicketId());
			  suborderDTO.setTicketPrice(ticketRegisterDTO.getPrice());
			  suborderDTO.setTicketRegisterId(ticketRegisterDTO.getId());
			  suborders.add(suborderDTO);
			  suborderMap.put(suborderDTO.getTicketId(), suborderDTO);
			  
		  }
		  List<RegistrationDTO> registrations = null;
		  RegistrationDTO registrationDTO = null;
		  for(TicketRegisterForm regForm : orderRegisterForm.getTicketRegForms()){
			  suborderDTO = suborderMap.get(regForm.getTicketId());
			  registrationDTO = new RegistrationDTO();
			  
			  registrationDTO.setCompany(regForm.getCompany());
			  registrationDTO.setCreatedBy(0L);
			  registrationDTO.setCreatedDate(new Date());
			  registrationDTO.setEmail(regForm.getEmail());
			  registrationDTO.setName(regForm.getName());
			  registrationDTO.setPhone(regForm.getPhone());
			  
			  if(suborderDTO.getRegistrations()==null){
				  registrations = new ArrayList<RegistrationDTO>();
				  suborderDTO.setRegistrations(registrations);
			  }
			  
			  suborderDTO.getRegistrations().add(registrationDTO);
			  
		  }
		  
		  
		  return orderDTO;
	  }
	  
}
