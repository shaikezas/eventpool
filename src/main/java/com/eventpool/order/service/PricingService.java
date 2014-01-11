package com.eventpool.order.service;

import com.eventpool.common.dto.PackageDTO;
import com.eventpool.common.entities.MemberShipPlan;


public interface PricingService {

	public PackageDTO getMembershipPlan(String currency);
}
