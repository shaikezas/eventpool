package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.TicketRegister;

public interface TicketRegisterRepository extends JpaRepository<TicketRegister, Long>{

}
