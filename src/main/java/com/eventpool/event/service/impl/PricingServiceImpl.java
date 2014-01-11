package com.eventpool.event.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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

import com.eventpool.common.dto.PackageDTO;
import com.eventpool.common.entities.MemberShipPlan;
import com.eventpool.common.module.EventpoolMapper;
import com.eventpool.common.repositories.MemberShipPlanRepository;
import com.eventpool.order.service.PaymentService;
import com.eventpool.order.service.PricingService;
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
public class PricingServiceImpl implements PricingService {
	
	static final Logger logger = LoggerFactory.getLogger(PricingServiceImpl.class);
	
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

	@Resource
	private MemberShipPlanRepository memberShipPlanRepository;

	@Override
	public PackageDTO getMembershipPlan(String currency) {
		MemberShipPlan memberShipPlan = memberShipPlanRepository.getMembershipPlan(currency);
		if(memberShipPlan==null){
			memberShipPlan = memberShipPlanRepository.getMembershipPlan("USD");
		}
		PackageDTO packageDTO = new PackageDTO();
		if(memberShipPlan!=null){
			packageDTO.setCurreny(memberShipPlan.getCurrency());
			packageDTO.setEventUrl(memberShipPlan.getEventUrl());
			packageDTO.setPlanName(memberShipPlan.getMemberShip().getName());
			packageDTO.setPrice(memberShipPlan.getFee());
			String features = memberShipPlan.getFeatures();
			Map<Integer, String> featureMap = new HashMap<Integer, String>();
			packageDTO.setFeatureMap(featureMap );
			if(features==null){
				features = memberShipPlan.getMemberShip().getFeatures();
			}
			if(features!=null){
				String[] split = features.split(",");
				if(split!=null && split.length>0){
					for(String splitItem:split){
						featureMap.put(Integer.parseInt(splitItem), "true");
					}
				}
			}
		}
		return packageDTO;
	}

}
