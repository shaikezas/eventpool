package com.eventpool.common.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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

	@Column(name="TOTAL_POINTS")
	private Integer totalPoints;

	@Column(name = "SETTINGS")
	private String settings;

	@Column(name="FEE")
	private Double fee;

	@Column(name="POINTS_PER_EVENT")
	private Integer pointsPerEvent;

	@Column(name = "FEATURES")
	private String features;
	
//	@OneToMany
//	@JoinColumn(name = "ID",referencedColumnName="MEMBERSHIP_ID")
	@Transient
	private List<MemberShipPlan> membershipPlans;
	
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

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getPointsPerEvent() {
		return pointsPerEvent;
	}

	public void setPointsPerEvent(Integer pointsPerEvent) {
		this.pointsPerEvent = pointsPerEvent;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public List<MemberShipPlan> getMembershipPlans() {
		return membershipPlans;
	}

	public void setMembershipPlans(List<MemberShipPlan> membershipPlans) {
		this.membershipPlans = membershipPlans;
	}

	
}
