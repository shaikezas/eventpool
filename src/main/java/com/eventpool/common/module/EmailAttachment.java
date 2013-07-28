package com.eventpool.common.module;

import java.io.ByteArrayOutputStream;

public class EmailAttachment {
	ByteArrayOutputStream attachment;
	String attachmentName;
	public ByteArrayOutputStream getAttachment() {
		return attachment;
	}
	public void setAttachment(ByteArrayOutputStream attachment) {
		this.attachment = attachment;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	
	
}
