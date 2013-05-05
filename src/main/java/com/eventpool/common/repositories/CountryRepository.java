package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.City;
import com.eventpool.common.entities.Country;
import com.eventpool.common.entities.Order;

public interface CountryRepository extends JpaRepository<Country, Integer>{

}
