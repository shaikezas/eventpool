package com.eventpool.common.image;


public enum ImageType {

	  SMALL(1),
	  MEDIUM(2),
	  LARGE(3);
		
	  private int description;
  
	  private ImageType(int type) {
		  description = type;
	  }
	  
	  public String value() {
	    return name();
	  }
	
	  public static ImageType fromValue(String v) {
	    return valueOf(v);
	  }

	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}
}
