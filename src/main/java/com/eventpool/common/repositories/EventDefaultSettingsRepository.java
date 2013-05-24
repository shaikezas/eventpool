package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.EventDefaultSettings;

public interface EventDefaultSettingsRepository extends JpaRepository<EventDefaultSettings, Long>{
	
	public EventDefaultSettings findByName(String name);

}
