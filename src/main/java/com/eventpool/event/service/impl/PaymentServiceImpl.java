package com.eventpool.event.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsReq;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsRequestType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AckCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DetailLevelCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

import com.eventpool.order.service.PaymentService;
import com.eventpool.web.controller.OrderController;
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
	
	static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
	
	@Value("$EVENT_POOL{url.prefix}")
	private String urlPrefix;

	@Value("$EVENT_POOL{pay.mode}")
	private String payMode;

	@Value("$EVENT_POOL{acct1.UserName}")
	private String payUserName;

	@Value("$EVENT_POOL{acct1.Password}")
	private String password;

	@Value("$EVENT_POOL{acct1.Signature}")
	private String signature;

	@Override
	public String initPayment(PayPalDTO payPalDTO) {
		PaymentDetailsType paymentDetails = new PaymentDetailsType();
		paymentDetails.setPaymentAction(PaymentActionCodeType.AUTHORIZATION);
		//fromValue("Sale"));
		double totalamount=0;
		List<PayPalItemDTO> payPalItemDTOs = payPalDTO.getPayPalItemDTOs();
		if(payPalItemDTOs!=null && payPalItemDTOs.size()>0){
			for(PayPalItemDTO palItemDTO:payPalItemDTOs){
				PaymentDetailsItemType item = new PaymentDetailsItemType();
				BasicAmountType amt = new BasicAmountType();
				amt.setCurrencyID(CurrencyCodeType.valueOf(payPalDTO.getCurrency()));
				String amount = palItemDTO.getAmount();
				amt.setValue(amount);
				int itemQuantity = palItemDTO.getItemQuantity();
				item.setQuantity(itemQuantity);//itemQuantity
				item.setName(palItemDTO.getItemName());
				item.setAmount(amt);
				List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
				lineItems.add(item);
				paymentDetails.setPaymentDetailsItem(lineItems);
				totalamount+=Double.parseDouble(amount)*itemQuantity;
			}
		}
		BasicAmountType orderTotal = new BasicAmountType();
		orderTotal.setCurrencyID(CurrencyCodeType.fromValue(payPalDTO.getCurrency()));
		orderTotal.setValue(String.valueOf(totalamount * 1)); 
		paymentDetails.setOrderTotal(orderTotal);
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();
		paymentDetailsList.add(paymentDetails);

		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();
		setExpressCheckoutRequestDetails.setReturnURL(urlPrefix+"success?oid="+payPalDTO.getOrderId());
		setExpressCheckoutRequestDetails.setCancelURL(urlPrefix+"failed?oid="+payPalDTO.getOrderId());
/*		setExpressCheckoutRequestDetails.setCallbackTimeout("10");
		setExpressCheckoutRequestDetails.setCallbackURL(urlPrefix+"failed?oid="+payPalDTO.getOrderId());
		setExpressCheckoutRequestDetails.setCallbackVersion("61.0");
*/
		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);

		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(setExpressCheckoutRequestDetails);
		setExpressCheckoutRequest.setVersion("104.0");

		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		setExpressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

		Map<String, String> sdkConfig = new HashMap<String, String>();
/*		sdkConfig.put("mode", "sandbox");
		sdkConfig.put("acct1.UserName", "jb-us-seller_api1.paypal.com");
		sdkConfig.put("acct1.Password", "WX4WTU3S8MY44S7F");
		sdkConfig.put("acct1.Signature","AFcWxV21C7fd0v3bYYYRCpSSRl31A7yDhhsPUU2XhtMoZXsWHFxu-RWy");
*/		
		sdkConfig.put("mode", payMode);
		sdkConfig.put("acct1.UserName", payUserName);
		sdkConfig.put("acct1.Password",password);
		sdkConfig.put("acct1.Signature",signature);
		
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		try {
			SetExpressCheckoutResponseType setExpressCheckoutResponse = service.setExpressCheckout(setExpressCheckoutReq);
			return 	setExpressCheckoutResponse.getToken();
		} catch (SSLConfigurationException e) {
			logger.error("token generaton error:",e);
		} catch (InvalidCredentialException e) {
			logger.error("token generaton error:",e);
		} catch (HttpErrorException e) {
			logger.error("token generaton error:",e);
		} catch (InvalidResponseDataException e) {
			logger.error("token generaton error:",e);
		} catch (ClientActionRequiredException e) {
			logger.error("token generaton error:",e);
		} catch (MissingCredentialException e) {
			logger.error("token generaton error:",e);
		} catch (OAuthException e) {
			logger.error("token generaton error:",e);
		} catch (IOException e) {
			logger.error("token generaton error:",e);
		} catch (InterruptedException e) {
			logger.error("token generaton error:",e);
		} catch (ParserConfigurationException e) {
			logger.error("token generaton error:",e);
		} catch (SAXException e) {
			logger.error("token generaton error:",e);
		}
		return null;
	}

	@Override
	public AckCodeType getPaymentDetails(String token){
		Map<String, String> sdkConfig = new HashMap<String, String>();
		sdkConfig.put("mode", payMode);
		sdkConfig.put("acct1.UserName", payUserName);
		sdkConfig.put("acct1.Password",password);
		sdkConfig.put("acct1.Signature",signature);
		
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(sdkConfig);
		GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
		GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequest = new GetExpressCheckoutDetailsRequestType();
		getExpressCheckoutDetailsRequest.setToken(token);
		getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequest );
		try {
				GetExpressCheckoutDetailsResponseType expressCheckoutDetails = service.getExpressCheckoutDetails(getExpressCheckoutDetailsReq);
				AckCodeType ack = expressCheckoutDetails.getAck();
				return ack;
		} catch (SSLConfigurationException e) {
			logger.error("token generaton error:",e);
		} catch (InvalidCredentialException e) {
			logger.error("token generaton error:",e);
		} catch (HttpErrorException e) {
			logger.error("token generaton error:",e);
		} catch (InvalidResponseDataException e) {
			logger.error("token generaton error:",e);
		} catch (ClientActionRequiredException e) {
			logger.error("token generaton error:",e);
		} catch (MissingCredentialException e) {
			logger.error("token generaton error:",e);
		} catch (OAuthException e) {
			logger.error("token generaton error:",e);
		} catch (IOException e) {
			logger.error("token generaton error:",e);
		} catch (InterruptedException e) {
			logger.error("token generaton error:",e);
		} catch (ParserConfigurationException e) {
			logger.error("token generaton error:",e);
		} catch (SAXException e) {
			logger.error("token generaton error:",e);
		}
		return null;
	}
}
