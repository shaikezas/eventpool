package com.eventpool.web.forms;

import java.io.File;

public class EventOptionsForm {

	private Integer classificationType = 1;
	private String faceBookUrl;
	private String keyWords;
	private String contactDetails;
	private String eventWebSiteUrl;
	private File organizerFile;
	private String organizer;
	private Long eventId;
	public Integer getClassificationType() {
		return classificationType;
	}
	public void setClassificationType(Integer classificationType) {
		this.classificationType = classificationType;
	}
	public String getFaceBookUrl() {
		return faceBookUrl;
	}
	public void setFaceBookUrl(String faceBookUrl) {
		this.faceBookUrl = faceBookUrl;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getEventWebSiteUrl() {
		return eventWebSiteUrl;
	}
	public void setEventWebSiteUrl(String eventWebSiteUrl) {
		this.eventWebSiteUrl = eventWebSiteUrl;
	}
	public File getOrganizerFile() {
		return organizerFile;
	}
	public void setOrganizerFile(File organizerFile) {
		this.organizerFile = organizerFile;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	
	
}
