package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_DEFAULT_SETTINGS")
public class EventDefaultSettings extends AuditableIdEntity {

	@Column(name = "SETTINGS")
	private String settings;

	@Column(name = "NAME")
	private String name;
	
	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

}
