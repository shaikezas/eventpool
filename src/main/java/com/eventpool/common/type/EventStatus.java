package com.eventpool.common.type;


public enum EventStatus {
  

  OPEN("Open"),
  CLOSED("Closed"),
  CANCELLED("Cancelled"),
  DRAFT("Draft");
  

  private String description;
  
  private EventStatus(String description)
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

  public static EventStatus fromValue(String v) {
    return valueOf(v);
  }
  
}
