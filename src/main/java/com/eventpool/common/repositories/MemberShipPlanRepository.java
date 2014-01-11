package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.MemberShip;
import com.eventpool.common.entities.MemberShipPlan;

public interface MemberShipPlanRepository extends JpaRepository<MemberShipPlan, Integer>{
	
	@Query(value="SELECT mem FROM MemberShipPlan mem WHERE mem.currency=?1")
	public List<MemberShipPlan> getMembershipPlan(String currency);


}
