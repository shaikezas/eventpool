package com.eventpool.common.type;


public enum EventInfoType {
  

  BASIC("BASIC"),
  BUYER("BUYER"),
  ATTENDEE("ATTENDEE");
  

  private String description;
  
  private EventInfoType(String description)
  {
    this.description = description;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String value() {
    return name();
  }

  public static EventInfoType fromValue(String v) {
    return valueOf(v);
  }
  
}
