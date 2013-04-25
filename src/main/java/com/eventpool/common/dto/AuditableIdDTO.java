package com.eventpool.common.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public abstract class AuditableIdDTO extends IdDTO {

	private Long createdBy;
	
	private Long modifiedBy;

	private Date createdDate = new Date();

	private Date modifiedDate = new Date();

	@PreUpdate
	public void onUpdate(){
		this.setModifiedDate(new Date());
	}

	@PrePersist
	public void onPersist(){
		setCreatedDate(new Date());
		onUpdate();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	
	public Long getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
