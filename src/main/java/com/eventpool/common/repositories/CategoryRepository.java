package com.eventpool.common.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventpool.common.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	@Query(nativeQuery=true,value="SELECT category.ID as categoryID, category.NAME as NAME, category.PARENT_CATEGORYID as parent_CategoryID, parent.NAME as parentCategoryName FROM CATEGORY AS category LEFT JOIN CATEGORY parent ON category.PARENT_CATEGORYID = parent.ID WHERE category.ACTIVE='Y'")
	public List<Object[]> getCategories();

}
