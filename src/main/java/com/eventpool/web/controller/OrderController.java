package com.eventpool.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.eventpool.common.entities.User;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.common.module.HtmlEmailService;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.common.type.TicketType;
import com.eventpool.event.service.impl.PayPalDTO;
import com.eventpool.order.service.OrderService;
import com.eventpool.order.service.PaymentService;
import com.eventpool.web.domain.ResponseMessage;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;
import com.google.gson.Gson;


@Controller
@RequestMapping("/order")
public class OrderController {

	static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Resource
	OrderService orderService;
	
	@Resource
	UserService  userService;
	
	@Resource
	PaymentService paymentService;
	
    @RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody OrderRegisterForm registerOrder(@RequestBody EventRegisterDTO eventRegister) throws NoTicketInventoryBlockedException {
		  OrderRegisterForm orderRegisterForm = null;
		  User user = userService.getCurrentUser();
		  try {
			  orderRegisterForm = orderService.registerOrder(eventRegister);
			  OrderDTO orderDTO = convertToOrderDTO(orderRegisterForm,user.getId());
			  Order Order = orderService.createOrder(orderDTO);
			  if(orderRegisterForm.getGrossAmount().compareTo(0.0)>0){
				  
				  PayPalDTO payPalDTO = new PayPalDTO();
				  payPalDTO.setAmount(orderRegisterForm.getGrossAmount().toString());
				  payPalDTO.setItemQuantity(orderRegisterForm.getTotalTickets());
				  payPalDTO.setSuccessUrl("http://localhost:8083/eventpool/#/order/success?oid="+Order.getId());
				  payPalDTO.setCancelUrl("http://localhost:8083/eventpool/#/order/");
				  payPalDTO.setCurrency(eventRegister.getPaymentCurrency().name());
				  
				  String token = paymentService.initPayment(payPalDTO);
				  orderRegisterForm.setToken(token);
				  orderService.updateToken(Order.getId(), token);
				  logger.info("Tocken:"+token);
			  }
			} catch(NoTicketInventoryBlockedException e){
				throw e;
			}
			catch (Exception e) {
				logger.info("not able to create order",e);
			}
		  return orderRegisterForm;
	 }
	  
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage createOrder(@RequestBody 
			OrderRegisterForm orderRegisterForm)  {
			  OrderStatusDTO status = new OrderStatusDTO();
			  User user = userService.getCurrentUser();
			  OrderDTO orderDTO = convertToOrderDTO(orderRegisterForm,user.getId());
			  
			  Order order = null;
			  try {
				  order = orderService.createOrder(orderDTO);
			} catch (Exception e) {
				 return new ResponseMessage(ResponseMessage.Type.error, "Failed to create a order : reason - "+e.getMessage());
			}
		    if(order==null){
			  return new ResponseMessage(ResponseMessage.Type.error, "Failed to create a order");
			}
			return new ResponseMessage(ResponseMessage.Type.success, "Successfully created the order, your orderId is :"+order.getId());
	    }
	  
	  private OrderDTO convertToOrderDTO(OrderRegisterForm orderRegisterForm,Long userId){
		  OrderDTO orderDTO = new OrderDTO();
		  orderDTO.setBillingAddress(orderRegisterForm.getBillingAddress());
		  orderDTO.setCreatedBy(userId);
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
			  suborderDTO.setCreatedBy(userId);
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
			  suborderDTO.getTicket().setPrice(ticketRegisterDTO.getPrice());
			  suborderDTO.getTicket().setPrice(ticketRegisterDTO.getPrice());
			  suborderDTO.setTicketRegisterId(ticketRegisterDTO.getId());
			  suborderDTO.getTicket().setName(ticketRegisterDTO.getTicketName());
			  suborders.add(suborderDTO);
			  suborderMap.put(suborderDTO.getTicket().getId(), suborderDTO);
			  suborderDTO.getTicket().setCreatedBy(userId);
			  suborderDTO.setTicketPrice(ticketRegisterDTO.getPrice());
		  }
		  List<RegistrationDTO> registrations = null;
		  RegistrationDTO registrationDTO = null;
		  for(TicketRegisterForm regForm : orderRegisterForm.getTicketRegForms()){
			  suborderDTO = suborderMap.get(regForm.getTicketId());
			  registrationDTO = new RegistrationDTO();
			  
			  registrationDTO.setCreatedBy(userId);
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
