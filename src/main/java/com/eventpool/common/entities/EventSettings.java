package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "EVENT_SETTINGS")
public class EventSettings extends AuditableEntity {

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EVENT_ID",insertable=false,updatable=false)
	private Event event;
	

	@Id
	@Column(name = "EVENT_ID")
	private Long eventId;
	
	@Column(name="GOOGLEMAP")
	@Type(type = "yes_no")
	private boolean showGoogleMap;

	@Column(name="ORGANIZER_NAME")
	@Type(type = "yes_no")
	private boolean showOrganizerName;

	@Column(name="ORGANIZER_DESC")
	@Type(type = "yes_no")
	private boolean showOrganizerDesc;

	@Column(name="CONTACT_DETAILS")
	@Type(type = "yes_no")
	private boolean showContactDetails;

	@Column(name="ATTEND_TYPE")
	@Type(type = "yes_no")
	private boolean showAttendType;

	@Column(name="OCCURING_TYPE")
	@Type(type = "yes_no")
	private boolean showOccuringType;

	@Column(name="SHOW_ATTENDE_LIST")
	@Type(type = "yes_no")
	private boolean showAttendeList;

	@Column(name="SHOW_REMAINING_TICKETS")
	@Type(type = "yes_no")
	private boolean showRemainingTickets;

	@Column(name="TERMS_CONDITIONS")
	@Type(type = "yes_no")
	private boolean showTermsAndConditions;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public boolean isShowGoogleMap() {
		return showGoogleMap;
	}

	public void setShowGoogleMap(boolean showGoogleMap) {
		this.showGoogleMap = showGoogleMap;
	}

	public boolean isShowOrganizerName() {
		return showOrganizerName;
	}

	public void setShowOrganizerName(boolean showOrganizerName) {
		this.showOrganizerName = showOrganizerName;
	}

	public boolean isShowOrganizerDesc() {
		return showOrganizerDesc;
	}

	public void setShowOrganizerDesc(boolean showOrganizerDesc) {
		this.showOrganizerDesc = showOrganizerDesc;
	}

	public boolean isShowContactDetails() {
		return showContactDetails;
	}

	public void setShowContactDetails(boolean showContactDetails) {
		this.showContactDetails = showContactDetails;
	}

	public boolean isShowAttendType() {
		return showAttendType;
	}

	public void setShowAttendType(boolean showAttendType) {
		this.showAttendType = showAttendType;
	}

	public boolean isShowOccuringType() {
		return showOccuringType;
	}

	public void setShowOccuringType(boolean showOccuringType) {
		this.showOccuringType = showOccuringType;
	}

	public boolean isShowAttendeList() {
		return showAttendeList;
	}

	public void setShowAttendeList(boolean showAttendeList) {
		this.showAttendeList = showAttendeList;
	}

	public boolean isShowRemainingTickets() {
		return showRemainingTickets;
	}

	public void setShowRemainingTickets(boolean showRemainingTickets) {
		this.showRemainingTickets = showRemainingTickets;
	}

	public boolean isShowTermsAndConditions() {
		return showTermsAndConditions;
	}

	public void setShowTermsAndConditions(boolean showTermsAndConditions) {
		this.showTermsAndConditions = showTermsAndConditions;
	}

	
}
