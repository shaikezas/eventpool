package com.eventpool.common.type;


public enum QuestionType {
  

  TEXT("Text"),
  DROPDOWN("DropDown"),
  CHECKBOX("CheckBox"),
  RADIO("Radio");
  

  private String description;
  
  private QuestionType(String description)
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

  public static QuestionType fromValue(String v) {
    return valueOf(v);
  }
  
}
