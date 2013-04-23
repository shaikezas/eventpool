package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "category")
public class Category extends AuditableIdEntity {

	@NotNull(message = "Can't be Empty")
	@Column(name = "name")
	private String name;
	
	@NotNull(message = "Can't be Empty")
	@Column(name = "description")
	private String description;
	
	
	@Column(name="isActive",nullable=false)
	@Type(type = "yes_no")
	private Boolean isActive;
	
	@Column(name = "keyDescription")
	private String keyDescription;
	
	
	@ManyToOne
	@JoinColumn(name = "parent_category", insertable = false, updatable = false)
	private Category parentCategory;
	


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


	public String getKeyDescription() {
		return keyDescription;
	}


	public void setKeyDescription(String keyDescription) {
		this.keyDescription = keyDescription;
	}


	public Category getParentCategory() {
		return parentCategory;
	}


	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	
}


