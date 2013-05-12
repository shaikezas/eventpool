package com.eventpool.common.dto;


public class CategoryDTO  {

	private String name;
	
	private String description;
	
	private Long id;
	
	private Boolean isActive;

	private String code;

	private CategoryDTO parentCategory;
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public CategoryDTO getParentCategory() {
		return parentCategory;
	}


	public void setParentCategory(CategoryDTO parentCategory) {
		this.parentCategory = parentCategory;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	
}


