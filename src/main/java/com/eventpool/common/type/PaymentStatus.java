package com.eventpool.common.type;


public enum PaymentStatus {
  
	REFUNDED("REFUNDED"),
	REFUND_FAILED("REFUND_FAILED"),
	REFUND_INITIATED("REFUND_INITIATED"),
	PAYMENT_SUCCESS("PAYMENT_SUCCESS"),
	PAYMENT_FAILED("PAYMENT_FAILED"),
	WAITING_FOR_GATEWAYRESPONSE("WAITING_FOR_GATEWAYRESPONSE");
  

  private String description;
  
  private PaymentStatus(String description)
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

  public static PaymentStatus fromValue(String v) {
    return valueOf(v);
  }
  
}
