package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.City;
import com.eventpool.common.entities.Order;

public interface CityRepository extends JpaRepository<City, Integer>{

	@Query(value="SELECT city FROM City city WHERE city.stateId=?1")
	public List<City> getCitiesForState(Integer stateId);

	@Query(value="SELECT city FROM City city WHERE city.countryId=?1")
	public List<City> getCitiesForCountry(Integer countryId);

}
