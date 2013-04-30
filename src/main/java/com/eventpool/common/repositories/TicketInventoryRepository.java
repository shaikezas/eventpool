package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.TicketInventory;

public interface TicketInventoryRepository extends JpaRepository<TicketInventory, Long>{

}
