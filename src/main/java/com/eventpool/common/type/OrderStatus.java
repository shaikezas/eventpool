package com.eventpool.common.type;


public enum OrderStatus {
  
  UNPAID("UNPAID"),
  PAID("PAID"),
  CANCELLED("CANCELLED"),
  CLOSED("CLOSED"),
  NEW("NEW");
  

  private String description;
  
  private OrderStatus(String description)
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

  public static OrderStatus fromValue(String v) {
    return valueOf(v);
  }
  
}
