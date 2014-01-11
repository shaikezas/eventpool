package com.eventpool.order.service;

import java.util.List;

import com.eventpool.common.dto.PackageDTO;
import com.eventpool.common.entities.MemberShipPlan;


public interface PricingService {

	public List<PackageDTO> getMembershipPlan(String currency);
}
