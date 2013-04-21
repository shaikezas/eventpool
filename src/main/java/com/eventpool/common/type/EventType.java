package com.eventpool.common.type;


public enum EventType {
  

  PAID("Paid Event"),
  FREE("Free Event"),
  DONATION("No Registration");
  

  private String description;
  
  private EventType(String description)
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

  public static EventType fromValue(String v) {
    return valueOf(v);
  }
  
}
