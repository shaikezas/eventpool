package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Media;

public interface EventMediaRepository extends JpaRepository<Media, Long>{

	@Query(value="SELECT media FROM Media media WHERE media.eventUrl=?1")
	public Media findEventUrl(String eventUrl);

}
