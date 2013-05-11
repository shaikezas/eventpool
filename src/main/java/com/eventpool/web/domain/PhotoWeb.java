package com.eventpool.web.domain;

public class PhotoWeb {
	String uniqueid;
	String piclink;
	String add_url;
    String thumbnail_url;
    String left_url;
    
    public PhotoWeb() {
    	
    }
    public PhotoWeb(String uniqueid, String mainlink, String thumblink, String leftlink, String addurl) {
    	this.uniqueid 		= uniqueid;
    	this.piclink		= mainlink;
    	this.thumbnail_url	= thumblink;
    	this.left_url		= leftlink;
    	this.add_url		= addurl;
    }
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	public String getPiclink() {
		return piclink;
	}
	public void setPiclink(String piclink) {
		this.piclink = piclink;
	}
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	public String getLeft_url() {
		return left_url;
	}
	public void setLeft_url(String left_url) {
		this.left_url = left_url;
	}
	public String getAdd_url() {
		return add_url;
	}
	public void setAdd_url(String add_url) {
		this.add_url = add_url;
	}
}

