package com.eventpool.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventpool.common.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
