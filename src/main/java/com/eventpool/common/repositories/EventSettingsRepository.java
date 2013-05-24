package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.EventSettings;

public interface EventSettingsRepository extends JpaRepository<EventSettings, Long>{
	

}
