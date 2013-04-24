package com.eventpool.common.entities;

import java.util.Date;
import java.util.List;

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
import com.eventpool.common.type.EventStatus;
import com.eventpool.common.type.EventType;

@Entity
@Table(name = "event")
public class Event extends AuditableIdEntity {

	@NotNull(message = "Can't be Empty")
	@Column(name = "title")
	private String title;

	@Column(name = "start_date", nullable = false)
	Date startDate;

	@Column(name = "end_date", nullable = false)
	Date endDate;
	
	
	@Column(name="isActive",nullable=false)
	@Type(type = "yes_no")
	private Boolean isActive;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private EventStatus status;

	@Column(name = "description", length = 15000)
	private String description;

	@Column(name="event_type")
	@Enumerated(EnumType.STRING)
	private EventType eventType;

	@NotNull(message = "Can't be Empty")
	@Column(name = "sub_category_id")
	private Integer subCategoryId;
	
	@Column(name = "company_logo_url")
	private String companyLogoUrl;

	@Column(name = "banner_url")
	private String bannerUrl;


	@Column(name = "venue_name")
	private String venueName;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address venueAddress;
	
	@Column(name = "organizer_name")
	private String organizerName;
	
	@Column(name = "organizer_description", length = 15000)
	private String organizerDescription;
	
	@Column(name = "contact_details",length=1000)
	private String contactDetails;
	
	@Column(name="event_info_type")
	@Enumerated(EnumType.STRING)
	private EventInfoType infoType;
	
	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="event_id",referencedColumnName="id")
	private List<Ticket> tickets;
	
	@Column(name="terms_conditions",length=1500)
	private String termsAndConditions;

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


	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}


	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}

	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
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

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
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

	@PostUpdate
	@PostPersist
	public void onPersist(){
		List<Ticket> listOfTickets = this.getTickets();
		if ( listOfTickets!= null && listOfTickets.size()>0){
			for ( Ticket ticket : listOfTickets){
				ticket.setEnventId(this.getId());
			}
		}
	}
	
	
	
}
