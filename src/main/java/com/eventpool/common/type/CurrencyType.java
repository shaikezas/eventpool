package com.eventpool.common.type;


public enum CurrencyType {
  

  USD("US Dollar"),
  EUR("Euro"),
  INR("Indian Rupee");
  

  private String description;
  
  private CurrencyType(String description)
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

  public static CurrencyType fromValue(String v) {
    return valueOf(v);
  }
  
}
