package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

}
