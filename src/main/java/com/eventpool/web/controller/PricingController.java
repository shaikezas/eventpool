package com.eventpool.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventpool.common.dto.EventRegisterDTO;
import com.eventpool.common.dto.PackageDTO;
import com.eventpool.common.exceptions.NoTicketInventoryBlockedException;
import com.eventpool.web.forms.OrderRegisterForm;

@Controller
@RequestMapping("/pricing")
public class PricingController {
	static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public @ResponseBody OrderRegisterForm buyPoints(@RequestBody EventRegisterDTO eventRegister) throws NoTicketInventoryBlockedException {
		  OrderRegisterForm orderRegisterForm = null;
		  logger.info("buying points..");
		  return orderRegisterForm;
	 }
	
	@RequestMapping(value = "/package", method = RequestMethod.GET)
	public @ResponseBody List<PackageDTO> getPackages() throws NoTicketInventoryBlockedException {
		List<PackageDTO> packageDTO = null;
		  return packageDTO;
	 }
}
