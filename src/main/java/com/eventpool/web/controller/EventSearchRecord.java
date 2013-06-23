package com.eventpool.web.controller;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.solr.client.solrj.beans.Field;


public class EventSearchRecord  implements Serializable{

	@Field
	private Long id;
	@Field
	private String title;
	@Field
	private Date startDate;
	@Field
	private Date endDate;
	@Field
	private Boolean isActive;
	@Field
	private String description;
	@Field
	private Long subCategoryId;
	@Field
	private String banner;
	@Field
	private String videoUrl;
	@Field
	private String faceBookUrl;
	@Field
	private String otherUrl1;
	@Field
	private String otherUrl2;
	@Field
	private String venueName;
	@Field
	private String address1;
	@Field
	private String address2;
	@Field
	private Integer cityId;
	@Field
	private String mapUrl;
	@Field
	private Long zipCode;
	@Field
	private String phoneNumber;
	@Field
	private String fax;
	@Field
	private String mobileNumber;
	@Field
	private String organizerName;
	@Field
	private String organizerDescription;
	@Field
	private String contactDetails;
	@Field
	private boolean isWebinar;
	@Field
	private String keyWords;
	@Field
	private Date publishDate;
	private boolean showEvent;
	@Field
	private String privacyType;// isPrivate is uplicate of this.
	@Field
	private String eventUrl;
	@Field
	private String eventWebSiteUrl;
	@Field
	private Long createdBy;
	@Field
	private String status;

	@Field
	private String eventType;
	@Field
	private String infoType;
	
	@Field
	private String companyLogoUrl;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public boolean isIsWebinar() {
		return isWebinar;
	}
	public void setIsWebinar(boolean isWebinar) {
		this.isWebinar = isWebinar;
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
	
	public String getEventUrl() {
		return eventUrl;
	}
	
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}
	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
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
	public Long getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	
}
