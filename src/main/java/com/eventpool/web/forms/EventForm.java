package com.eventpool.web.forms;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.eventpool.common.dto.OrderFormSettings;
import com.eventpool.common.dto.UserEventSettingDTO;
import com.eventpool.common.module.EventPoolConstants;

public class EventForm  {

	private String title;
	private Long id;
	private String startDate;
	private String endDate;
	private Boolean isActive;
	private String description;
	private Long subCategoryId;
	private File organizerLogo;
	private String banner;
	private File bannerFile;
	private String videoUrl;
	private String faceBookUrl;
	private String otherUrl1;
	private String otherUrl2;
	private String venueName;
	private String address1;
	private String address2;
	private Integer cityId;
	private String mapUrl;
	private Long zipCode;
	private String phoneNumber;
	private String fax;
	private String mobileNumber;
	private String organizerName;
	private String organizerDescription;
	private String contactDetails;
	private List<TicketForm> tickets = new ArrayList<TicketForm>();
	private Boolean isWebinar = false;
	private String termsAndConditions;
	private String keyWords;
	private Date publishDate;
	private boolean showEvent;
	private String privacyType;// isPrivate is uplicate of this.
	private Boolean isPrivate=false;
	private Boolean isPublish=false;
	private String eventUrl;
	private Long eventId;
	private String eventWebSiteUrl;
	private Long addressId;
	private Long createdBy;
	private String venueAddress="" ; 
	private String cityName;
	private String stateName;
	private String countryName;
	private Integer classificationType = 1;
	private UserEventSettingDTO userEventSettingDTO;
	private int registrationLimit= EventPoolConstants.REGISTRATION_TIME_LIMIT;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public File getOrganizerLogo() {
		return organizerLogo;
	}
	public void setOrganizerLogo(File organizerLogo) {
		this.organizerLogo = organizerLogo;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getFaceBookUrl() {
		return faceBookUrl;
	}
	public void setFaceBookUrl(String faceBookUrl) {
		this.faceBookUrl = faceBookUrl;
	}
	public String getOtherUrl1() {
		return otherUrl1;
	}
	public void setOtherUrl1(String otherUrl1) {
		this.otherUrl1 = otherUrl1;
	}
	public String getOtherUrl2() {
		return otherUrl2;
	}
	public void setOtherUrl2(String otherUrl2) {
		this.otherUrl2 = otherUrl2;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public Long getZipCode() {
		return zipCode;
	}
	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public List<TicketForm> getTickets() {
		return tickets;
	}
	public void setTickets(List<TicketForm> tickets) {
		this.tickets = tickets;
	}
	public Boolean isIsWebinar() {
		return isWebinar;
	}
	public void setIsWebinar(Boolean isWebinar) {
		this.isWebinar = isWebinar;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
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
	public String getPrivacyType() {
		return privacyType;
	}
	public void setPrivacyType(String privacyType) {
		this.privacyType = privacyType;
	}
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public Boolean getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	
	public String getEventUrl() {
		return eventUrl;
	}
	
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	
	public String getEventWebSiteUrl() {
		return eventWebSiteUrl;
	}
	
	public void setEventWebSiteUrl(String eventWebSiteUrl) {
		this.eventWebSiteUrl = eventWebSiteUrl;
	}
	
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public int getRegistrationLimit() {
		return registrationLimit;
	}
	
	public void setRegistrationLimit(int registrationLimit) {
		this.registrationLimit = registrationLimit;
	}
	public File getBannerFile() {
		return bannerFile;
	}
	public void setBannerFile(File bannerFile) {
		this.bannerFile = bannerFile;
	}
	
	public String getVenueAddress() {
		venueAddress =  venueName!=null ? venueName+", ":"" ;
		venueAddress = venueAddress.concat(address1!=null?address1+", ":"");
		venueAddress =  venueAddress.concat(address2!=null?address2+", ":"");
		venueAddress =  venueAddress.concat(cityName!=null?cityName+", ":"");
		venueAddress =  venueAddress.concat(stateName!=null?stateName+", ":"");
		venueAddress = venueAddress.concat(countryName!=null?countryName+", ":"");
		venueAddress = venueAddress.concat(zipCode!=null?zipCode+"":"");
		
		if(venueAddress.trim().endsWith(",")){
		venueAddress = venueAddress.substring(0,venueAddress.lastIndexOf(","));
		}
		if(isWebinar!=null && isWebinar){
			venueAddress = "Webinar";
		}
		
		return venueAddress;
	}
	
	public void setVenueAddress(String venueAddress) {
		this.venueAddress = venueAddress;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getClassificationType() {
		return classificationType;
	}
	public void setClassificationType(Integer classificationType) {
		this.classificationType = classificationType;
	}
	public UserEventSettingDTO getUserEventSettingDTO() {
		return userEventSettingDTO;
	}
	public void setUserEventSettingDTO(UserEventSettingDTO userEventSettingDTO) {
		this.userEventSettingDTO = userEventSettingDTO;
	}
	
	

	
}
