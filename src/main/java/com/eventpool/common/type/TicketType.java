package com.eventpool.common.type;


public enum TicketType {
  

  PAID("Paid Event"),
  FREE("Free Event"),
  DONATION("No Registration");
  

  private String description;
  
  private TicketType(String description)
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

  public static TicketType fromValue(String v) {
    return valueOf(v);
  }
  
}
