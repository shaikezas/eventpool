package com.eventpool.common.type;


public enum GENDER {
  

  MALE("Male"),
  FEMALE("Female");
  

  private String description;
  
  private GENDER(String description)
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

  public static GENDER fromValue(String v) {
    return valueOf(v);
  }
  
}
