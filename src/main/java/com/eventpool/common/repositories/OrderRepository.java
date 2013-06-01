package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query(value="SELECT order FROM Order order WHERE order.createdBy=?1")
	public List<Order> getAllOrders(Long userId);

}
