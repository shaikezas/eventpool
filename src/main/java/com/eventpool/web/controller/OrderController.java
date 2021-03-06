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

import urn.ebay.apis.eBLBaseComponents.AckCodeType;

import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.OrderDTO;
import com.eventpool.common.dto.OrderStatusDTO;
import com.eventpool.common.dto.RegistrationDTO;
import com.eventpool.common.dto.SuborderDTO;
import com.eventpool.common.dto.TicketDTO;
import com.eventpool.common.dto.TicketInventoryDetails;
import com.eventpool.common.dto.TicketRegisterDTO;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.TicketRegister;
import com.eventpool.common.entities.User;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.common.repositories.OrderRepository;
import com.eventpool.common.type.OrderStatus;
import com.eventpool.common.type.PaymentStatus;
import com.eventpool.event.service.impl.PayPalDTO;
import com.eventpool.event.service.impl.PayPalItemDTO;
import com.eventpool.order.service.OrderService;
import com.eventpool.order.service.PaymentService;
import com.eventpool.ticket.commands.TicketUnBlockedCommand;
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
		  try {
			  orderRegisterForm = orderService.registerOrder(eventRegister);
			} catch(NoTicketInventoryBlockedException e){
				throw e;
			}
			catch (Exception e) {
				logger.info("not able to create order",e);
			}
		  return orderRegisterForm;
	 }

	private Order createOrder(OrderRegisterForm orderRegisterForm, User user) throws Exception {
		  OrderDTO orderDTO = convertToOrderDTO(orderRegisterForm,user.getId());
		  Order order = orderService.createOrder(orderDTO);
		  if(orderRegisterForm.getGrossAmount().compareTo(0.0)>0){
			  
			  PayPalDTO payPalDTO = new PayPalDTO();
			  payPalDTO.setOrderId(order.getId());
			  payPalDTO.setCurrency(orderRegisterForm.getPaymentCurrency().name());
			  for(TicketRegisterDTO ticketRegisterDTO:orderRegisterForm.getTicketRegisters()){
				  PayPalItemDTO payPalItemDTO = new PayPalItemDTO();
				  payPalItemDTO.setAmount(ticketRegisterDTO.getPrice().toString());
				  payPalItemDTO.setItemQuantity(ticketRegisterDTO.getQty());
				  payPalItemDTO.setItemName(ticketRegisterDTO.getTicketName());
				  payPalDTO.getPayPalItemDTOs().add(payPalItemDTO);
			  }
			  String token = paymentService.initPayment(payPalDTO);
			  orderRegisterForm.setToken(token);
			  orderService.updateToken(order.getId(), token);
			  logger.info("Token:"+token);
		  }
		  orderRegisterForm.setOid(order.getId());
		  return order;
	}
	  
    @RequestMapping(value = "/success", method = RequestMethod.GET)
  	public @ResponseBody OrderDTO successOrder(HttpServletRequest request)
    {
    	
    	String oid = request.getParameter("oid");
    	String token = request.getParameter("token");
    	String payerId = request.getParameter("PayerID");
    	
    	try {
    		if(token!=null && !token.equalsIgnoreCase("undefined")){
    			Date start = new Date();
	    		//AckCodeType ack = paymentService.getPaymentDetails(token);
	    		//if(ack == null ){
	    			//retry
	    		//}
	    		//if(ack==AckCodeType.SUCCESS){
	    			OrderDTO orderDTO = orderService.postOrder(Long.parseLong(oid), token, payerId);
	    			logger.info("successfully processed order.");
	    			Date end = new Date();
	    			logger.info("TIme taken for order procesing"+(end.getTime()-start.getTime()));
	    			return orderDTO;
	    		//}else if(ack==AckCodeType.FAILURE){
	    			//
	    		//}else if(ack==AckCodeType.SUCCESSWITHWARNING){
	    			//
	    		//}else{
	    			
	    		//}
    		}else{
    			return orderService.postOrder(Long.parseLong(oid), token, payerId);
    		}
    		
		} catch (NumberFormatException e) {
			logger.error(" unable to complete the order process",e);
			return null;
		} catch (Exception e) {
			logger.error(" unable to complete the order process",e);
			return null;
		}
    	//return null;
    }
    @RequestMapping(value = "/failed", method = RequestMethod.GET)
  	public @ResponseBody ResponseMessage failedOrder(HttpServletRequest request)
    {
    	String oid = request.getParameter("oid");
    	String token = request.getParameter("token");
    	String payerId = request.getParameter("PayerID");
    	
    	System.out.println("Oid - "+oid);
    	System.out.println("Token - "+token);
    	System.out.println("PayerId - "+payerId);
    	orderService.releaseInventory(Long.parseLong(oid));
    	return new ResponseMessage(ResponseMessage.Type.error, "Order creation failed.");
    }
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody OrderRegisterForm createOrder(@RequestBody 
				OrderRegisterForm orderRegisterForm)  {
		
			  OrderStatusDTO status = new OrderStatusDTO();
			  User user = userService.getCurrentUser();
			  OrderDTO orderDTO = convertToOrderDTO(orderRegisterForm,user.getId());
			  Order order = null;
			  try {
				  order = createOrder(orderRegisterForm, user);
			} catch (Exception e) {
				 logger.info("order creation failed",e);
				 //return new ResponseMessage(ResponseMessage.Type.error, "Failed to create a order : reason - "+e.getMessage());
			}
		    if(order==null){
		    	logger.info("could not created order");
			  //return new ResponseMessage(ResponseMessage.Type.error, "Failed to create a order");
			}
		    logger.info("TimeLeft - "+orderRegisterForm.getTimeLeft());
			return orderRegisterForm;
			//new ResponseMessage(ResponseMessage.Type.success, "Successfully created the order, your orderId is :"+order.getId());
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
			  suborderDTO.setStatus(OrderStatus.INITIATED);
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
