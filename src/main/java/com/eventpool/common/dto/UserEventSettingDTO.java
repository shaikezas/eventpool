package com.eventpool.common.dto;


public class UserEventSettingDTO {
	
	private boolean showOrgName;
	private boolean showOrgDesc;
	private boolean contactDetails;
	private boolean showHostWebsite;
	private boolean showAttendeDetails;
	
	public boolean isShowOrgName() {
		return showOrgName;
	}
	public void setShowOrgName(boolean showOrgName) {
		this.showOrgName = showOrgName;
	}
	public boolean isShowOrgDesc() {
		return showOrgDesc;
	}
	public void setShowOrgDesc(boolean showOrgDesc) {
		this.showOrgDesc = showOrgDesc;
	}
	public boolean isContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(boolean contactDetails) {
		this.contactDetails = contactDetails;
	}
	public boolean isShowHostWebsite() {
		return showHostWebsite;
	}
	public void setShowHostWebsite(boolean showHostWebsite) {
		this.showHostWebsite = showHostWebsite;
	}
	public boolean isShowAttendeDetails() {
		return showAttendeDetails;
	}
	public void setShowAttendeDetails(boolean showAttendeDetails) {
		this.showAttendeDetails = showAttendeDetails;
	}
	
}
