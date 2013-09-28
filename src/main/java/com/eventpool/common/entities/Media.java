package com.eventpool.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EVENT_MEDIA")
public class Media implements Serializable{

	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EVENT_ID",insertable=false,updatable=false)
	private Event event;
	

	@Id
	@Column(name = "EVENT_ID")
	private Long eventId;

	@Column(name = "PROMOTION_LOGO_URL")
	private String promotionLogoUrl;

	@Column(name = "BANNER_URL")
	private String bannerUrl;
	
	@Column(name = "VIDEO_URL")
	private String videoUrl;

	@Column(name = "FACEBOOK_URL")
	private String faceBookUrl;
	
	@Column(name = "OTHER_URL1")
	private String otherUrl1;

	@Column(name = "OTHER_URL2")
	private String otherUrl2;
	
	@Column(name = "EVENT_WEBSITE_URL")
	private String eventWebSiteUrl;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public String getPromotionLogoUrl() {
		return promotionLogoUrl;
	}

	public void setPromotionLogoUrl(String promotionLogoUrl) {
		this.promotionLogoUrl = promotionLogoUrl;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
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
	
	
	public String getEventWebSiteUrl() {
		return eventWebSiteUrl;
	}

	public void setEventWebSiteUrl(String eventWebSiteUrl) {
		this.eventWebSiteUrl = eventWebSiteUrl;
	}
	

}
