package com.eventpool.common.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;

public class EventDTO extends AuditableIdDTO {

	private String title;

	Date startDate;

	Date endDate;
	
	private Boolean isActive;
	
	private EventStatus status;

	private String description;

	private EventType eventType;

	private Integer subCategoryId;
	

	private MediaDTO media; 

	private String venueName;
	
	private AddressDTO venueAddress;
	
	private String organizerName;
	
	private String organizerDescription;
	
	private String contactDetails;
	
	private EventInfoType infoType;
	
	private List<TicketDTO> tickets;
	
	private String termsAndConditions;
	private String keyWords;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}




	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}


	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	
	public AddressDTO getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(AddressDTO venueAddress) {
		this.venueAddress = venueAddress;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public String getOrganizerDescription() {
		return organizerDescription;
	}

	public void setOrganizerDescription(String organizerDescription) {
		this.organizerDescription = organizerDescription;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<TicketDTO> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketDTO> tickets) {
		this.tickets = tickets;
	}

	
	
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public EventInfoType getInfoType() {
		return infoType;
	}

	public void setInfoType(EventInfoType infoType) {
		this.infoType = infoType;
	}
	
	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}



	public MediaDTO getMedia() {
		return media;
	}

	public void setMedia(MediaDTO media) {
		this.media = media;
	}
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
}
