package com.eventpool.web.domain;

import java.util.List;

public class UploadedFileResponse {
	private boolean status = false;
    private String  error = null;
    private List<PhotoWeb>  filesuploaded;
	
    public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<PhotoWeb> getFilesuploaded() {
		return filesuploaded;
	}
	public void setFilesuploaded(List<PhotoWeb> filesuploaded) {
		this.filesuploaded = filesuploaded;
	}
    
}

