package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.City;
import com.eventpool.common.entities.Order;

public interface CityRepository extends JpaRepository<City, Integer>{

}
