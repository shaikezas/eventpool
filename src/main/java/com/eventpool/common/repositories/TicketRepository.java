package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventpool.common.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
}
