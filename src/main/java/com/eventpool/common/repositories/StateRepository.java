package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.State;

public interface StateRepository extends JpaRepository<State, Integer>{

	@Query(value="SELECT state FROM State state WHERE state.countryId=?1")
	public List<State> getStatesForCountry(Integer countryId);

}
