package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.City;
import com.eventpool.common.entities.Order;
import com.eventpool.common.entities.State;

public interface StateRepository extends JpaRepository<State, Integer>{

}
