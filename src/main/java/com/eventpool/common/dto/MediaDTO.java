package com.eventpool.common.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class MediaDTO implements Serializable{

	private Long eventId;

	private String promotionLogoUrl;

	private String bannerUrl;
	
	private String videoUrl;

	private String faceBookUrl;
	
	private String otherUrl1;

	private String otherUrl2;

	private String evenWebSiteUrl;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
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

	public String getEvenWebSiteUrl() {
		return evenWebSiteUrl;
	}

	public void setEvenWebSiteUrl(String evenWebSiteUrl) {
		this.evenWebSiteUrl = evenWebSiteUrl;
	}
	
	

}
