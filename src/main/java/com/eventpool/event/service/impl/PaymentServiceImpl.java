package com.eventpool.event.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

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
import com.eventpool.common.type.EventInfoType;
import com.eventpool.event.service.impl.EventSettingsService;
import com.eventpool.order.service.PaymentService;
import com.eventpool.ticket.commands.TicketBlockedCommand;
import com.eventpool.ticket.commands.TicketOrderedCommand;
import com.eventpool.ticket.service.TicketInventoryService;
import com.eventpool.ticket.service.TicketInventoryUnblockedService;
import com.eventpool.web.controller.EventService;
import com.eventpool.web.domain.UserService;
import com.eventpool.web.forms.OrderRegisterForm;
import com.eventpool.web.forms.TicketRegisterForm;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

@SuppressWarnings("rawtypes")
@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public String initPayment(OrderDTO orderDTO) {
		PaymentDetailsType paymentDetails = new PaymentDetailsType();
		paymentDetails.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
		PaymentDetailsItemType item = new PaymentDetailsItemType();
		BasicAmountType amt = new BasicAmountType();
		amt.setCurrencyID(CurrencyCodeType.fromValue("USD"));
		double itemAmount = 1.00;
		amt.setValue(String.valueOf(itemAmount));
		int itemQuantity = 1;
		item.setQuantity(itemQuantity);
		item.setName("item");
		item.setAmount(amt);
			

		List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
		lineItems.add(item);
		paymentDetails.setPaymentDetailsItem(lineItems);
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue("USD"));
		orderTotal.setValue(String.valueOf(itemAmount * itemQuantity)); 
		paymentDetails.setOrderTotal(orderTotal);
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
		paymentDetailsList.add(paymentDetails);

		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
		setExpressCheckoutRequestDetails.setReturnURL("https://devtools-paypal.com/guide/expresscheckout?success=true");
		setExpressCheckoutRequestDetails.setCancelURL("https://devtools-paypal.com/guide/expresscheckout?cancel=true");

		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);

		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(setExpressCheckoutRequestDetails);
		setExpressCheckoutRequest.setVersion("104.0");

		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		setExpressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", "sandbox");
		sdkConfig.put("acct1.UserName", "jb-us-seller_api1.paypal.com");
		sdkConfig.put("acct1.Password", "WX4WTU3S8MY44S7F");
		sdkConfig.put("acct1.Signature","AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		try {
			SetExpressCheckoutResponseType setExpressCheckoutResponse = service.setExpressCheckout(setExpressCheckoutReq);
			return 	setExpressCheckoutResponse.getToken();
		} catch (SSLConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientActionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
