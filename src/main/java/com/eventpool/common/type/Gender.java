package com.eventpool.common.type;


public enum Gender {
  

  MALE("Male"),
  FEMALE("Female");
  

  private String description;
  
  private Gender(String description)
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

  public static Gender fromValue(String v) {
    return valueOf(v);
  }
  
}
