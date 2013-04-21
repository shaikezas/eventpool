package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
