package com.eventpool.common.dto;


public class EventSettingsDTO extends AuditableIdDTO {

	private Long eventId;
	private boolean showGoogleMap;
	private boolean showOrganizerName;
	private boolean showOrganizerDesc;
	private boolean showContactDetails;
	private boolean showAttendType;
	private boolean showOccuringType;
	private boolean showAttendeList;
	private boolean showRemainingTickets;
	private boolean showTermsAndConditions;

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
