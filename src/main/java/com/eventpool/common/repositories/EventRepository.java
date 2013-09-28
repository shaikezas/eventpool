package com.eventpool.common.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Event;
import com.eventpool.common.entities.Media;
import com.eventpool.common.entities.Ticket;
import com.eventpool.common.type.EventStatus;

public interface EventRepository extends JpaRepository<Event, Long>{

	@Query(value="SELECT event FROM Event event WHERE event.createdBy=?1")
	public List<Event> getAllEvents(Long userId);
	
	@Modifying
	@Query(value="UPDATE Event event SET event.publish=?2,event.status='OPEN',publishDate=?3 WHERE event.id = ?1")
	public void publish(Long id,boolean publish,Date date);

	@Modifying
	@Query(value="UPDATE Event event SET event.publish=?2,event.status='OPEN',publishDate=?3 WHERE event.eventUrl = ?1")
	public void publish(String eventUrl,boolean publish,Date date);

	@Query(value="SELECT event FROM Event event WHERE event.eventUrl =?1")
	public Event getByEventUrl(String eventUrl);
	
	@Query(value="SELECT event FROM Event event WHERE event.status=?1")
	public List<Event> getAllEvents(EventStatus status);
	
	@Query(value="SELECT event FROM Event event WHERE event.createdBy=?1 and event.status=?2 order by createdDate desc")
	public List<Event> getAllEvents(Long userId,EventStatus status);

	@Query(value="SELECT ticket FROM Ticket ticket WHERE ticket.eventId=?1")
	public List<Ticket> getEventTickets(Long eventId);
	
	@Query(value="SELECT event FROM Event event WHERE event.eventUrl=?1")
	public Event findEventUrl(String eventUrl);

}
