package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

	@Query(value="SELECT event FROM Event event WHERE event.createdBy=?1")
	public List<Event> getAllEvents(Long userId);

}
