package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "MEMBERSHIP")
public class MemberShip extends AbstractEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name="EXP_DAYS")
	private Integer expDays;

	@Column(name="NO_OF_EVENTS")
	private Integer noOfEvents;

	@Column(name = "SETTINGS")
	private String settings;

	@Column(name="FEE")
	private Double fee;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExpDays() {
		return expDays;
	}

	public void setExpDays(Integer expDays) {
		this.expDays = expDays;
	}

	public Integer getNoOfEvents() {
		return noOfEvents;
	}

	public void setNoOfEvents(Integer noOfEvents) {
		this.noOfEvents = noOfEvents;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	
}
