package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MEMBERSHIP_PLAN")
public class MemberShipPlan extends AbstractEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="MEMBERSHIP_ID")
	private Integer membershipId;

	@Column(name="EVENT_URL")
	private String eventUrl;

	@Column(name = "CURRENCY")
	private String currency;
	
	@Column(name = "SETTINGS")
	private String settings;

	@Column(name = "FEATURES")
	private String features;

	@Column(name="FEE")
	private Double fee;

	@Transient
//	@Column(name="MEMBERSHIP_ID")
	private MemberShip memberShip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(Integer membershipId) {
		this.membershipId = membershipId;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

	public MemberShip getMemberShip() {
		return memberShip;
	}

	public void setMemberShip(MemberShip memberShip) {
		this.memberShip = memberShip;
	}

}
