package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "CATEGORY")
public class Category extends AuditableIdEntity {

	@NotNull(message = "Can't be Empty")
	@Column(name = "NAME")
	private String name;
	
	@NotNull(message = "Can't be Empty")
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	@Column(name="ACTIVE",nullable=false)
	@Type(type = "yes_no")
	private Boolean isActive;

	@Column(name = "CODE")
	private String code;

	@ManyToOne
	@JoinColumn(name = "PARENT_CATEGORYID", insertable = false, updatable = false)
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


	public Category getParentCategory() {
		return parentCategory;
	}


	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	
}


