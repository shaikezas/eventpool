package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Suborder;

public interface SuborderRepository extends JpaRepository<Suborder, Long>{

	@Query(value="SELECT suborder FROM Suborder suborder WHERE suborder.createdBy=?1")
	public List<Suborder> getSuborders(Long userId);

}
