package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.Registration;
import com.eventpool.common.entities.Suborder;
import com.eventpool.common.entities.TicketSnapShot;

public interface SuborderRepository extends JpaRepository<Suborder, Long>{

	@Query(value="SELECT suborder FROM Suborder suborder WHERE suborder.createdBy=?1  order by createdDate desc")
	public List<Suborder> getSuborders(Long userId);

	@Query(value="SELECT ticketSnapShot FROM TicketSnapShot ticketSnapShot WHERE ticketSnapShot.createdBy=?1")
	public List<TicketSnapShot> getTicketSnapshots(Long userId);

	@Query(value="SELECT suborder FROM Suborder suborder,Ticket ticket WHERE ticket.eventId=?1 AND ticket.id=suborder.ticketSnapShot.ticketId")
	public List<Suborder> getEventSuborders(Long eventId);

	@Query(value="SELECT suborder FROM Suborder suborder WHERE suborder.ticketId=?1 and suborder.status='SUCCESS'")
	public List<Suborder> getAttendes(Long ticketId);

	@Query(value="SELECT suborder.order FROM Suborder suborder WHERE suborder.ticketId=?1 and suborder.status='SUCCESS'")
	public List<Order> getBuyers(Long ticketId);

}
