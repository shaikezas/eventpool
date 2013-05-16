package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

	@Query(value="SELECT event FROM Event event WHERE event.createdBy=?1")
	public List<Event> getAllEvents(Long userId);
	
	@Modifying
	@Query(value="UPDATE Event event SET event.publish=?2 WHERE event.id = ?1")
	public void publish(Long id,boolean publish);

	@Modifying
	@Query(value="UPDATE Event event SET event.publish=?2 WHERE event.id in (SELECT eventMedia.id FROM Media eventMedia where  eventMedia.eventUrl= ?1)")
	public void publish(String id,boolean publish);
	
}
