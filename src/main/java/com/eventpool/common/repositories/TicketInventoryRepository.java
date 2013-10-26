package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.TicketInventory;
import com.eventpool.common.entities.Ticket;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketInventoryRepository extends JpaRepository<TicketInventory, Long>{
//	SELECT ti.MAX_QTY FROM  ticket_inventory ti,ticket t WHERE t.EVENT_ID=2 AND t.ID=ti.TICKET_ID
	
	@Query(value = "SELECT ti FROM  TicketInventory ti, Ticket t WHERE t.id=ti.ticketId and t.deleted!=true and t.eventId=:eventId ")
	public List<TicketInventory> findTicketInventoryByEventId(@Param("eventId")Long eventId);
}
