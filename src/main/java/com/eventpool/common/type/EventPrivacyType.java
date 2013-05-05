package com.eventpool.common.type;


public enum EventPrivacyType {
  

  PUBLIC("public"),
  PRIVATE("private");
  

  private String description;
  
  private EventPrivacyType(String description)
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

  public static EventPrivacyType fromValue(String v) {
    return valueOf(v);
  }
  
}
