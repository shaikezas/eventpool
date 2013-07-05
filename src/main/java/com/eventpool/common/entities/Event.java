package com.eventpool.common.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.eventpool.common.type.EventInfoType;
import com.eventpool.common.type.EventPrivacyType;
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;

@Entity
@Table(name = "EVENT")
public class Event extends AuditableIdEntity {

	@NotNull(message = "Can't be Empty")
	@Column(name = "TITLE")
	private String title;

	@Column(name = "START_DATE", nullable = false)
	Date startDate;

	@Column(name = "END_DATE", nullable = false)
	Date endDate;
	
	
	@Column(name="ACTIVE",nullable=false)
	@Type(type = "yes_no")
	private Boolean isActive;
	
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private EventStatus status;

	@Column(name = "DESCRIPTION", length = 15000)
	private String description;

	@Column(name="PAY_TYPE")
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	//@NotNull(message = "Can't be Empty")
	@Column(name = "SUBCATEGORY_ID")
	private Integer subCategoryId;
	
	@NotNull
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="event")
	private Media media; 

	@Column(name = "VENUE_NAME")
	private String venueName;
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	@JoinColumn(name="VENUE_ADDRESS_ID",referencedColumnName="ID")
	private Address venueAddress;
	
	@Column(name = "ORGANIZER_NAME")
	private String organizerName;
	
	@Column(name = "ORGANIZER_DESC", length = 15000)
	private String organizerDescription;
	
	@Column(name = "CONTACT_DETAILS",length=1000)
	private String contactDetails;
	
	@Column(name="ATTEND_TYPE")
	@Enumerated(EnumType.STRING)
	private EventInfoType infoType;
	
	@OneToMany(fetch = FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="EVENT_ID",referencedColumnName="ID")
	private List<Ticket> tickets;
	
	@Column(name="TERMS_CONDITIONS",length=1500)
	private String termsAndConditions;

	@Column(name="KEY_WORDS",length=256)
	private String keyWords;

	@Column(name="WEBINAR",length=256)
	@Type(type = "yes_no")
	private boolean isWebinar=false;
	
	@Column(name="PUBLISH",length=1)
	@Type(type = "yes_no")
	private boolean publish;

	@Column(name = "PUBLISH_DATE", nullable = false)
	private Date publishDate;

	@Column(name="SHOW_EVENT",length=256)
	@Type(type = "yes_no")
	private boolean showEvent;

	@Column(name="PRIVACY_TYPE",length=256)
	@Enumerated(EnumType.STRING)
	private EventPrivacyType privacyType;

	@NotNull
	@Column(name = "EVENT_URL")
	private String eventUrl;

	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="event")
	private EventSettings eventSettings; 

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

	
	public Address getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(Address venueAddress) {
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

	@PostUpdate
	@PostPersist
	public void onPersist(){
		List<Ticket> listOfTickets = this.getTickets();
		if ( listOfTickets!= null && listOfTickets.size()>0){
			for ( Ticket ticket : listOfTickets){
				ticket.setEventId(this.getId());
			}
		}
		Media childMedia = this.getMedia();
		if(childMedia!=null){
			childMedia.setEventId(this.getId());
		}
		EventSettings eventSettings = this.getEventSettings();
		if(eventSettings!=null){
			eventSettings.setEventId(this.getId());
			eventSettings.setCreatedBy(getCreatedBy());
		}
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public boolean getIsWebinar() {
		return isWebinar;
	}

	public void setIsWebinar(boolean isWebinar) {
		this.isWebinar = isWebinar;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public boolean isShowEvent() {
		return showEvent;
	}

	public void setShowEvent(boolean showEvent) {
		this.showEvent = showEvent;
	}

	public EventPrivacyType getPrivacyType() {
		return privacyType;
	}

	public void setPrivacyType(EventPrivacyType privacyType) {
		this.privacyType = privacyType;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public EventSettings getEventSettings() {
		return eventSettings;
	}

	public void setEventSettings(EventSettings eventSettings) {
		this.eventSettings = eventSettings;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

}
