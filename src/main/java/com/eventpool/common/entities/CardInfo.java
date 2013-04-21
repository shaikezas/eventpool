package com.eventpool.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Embeddable
@MappedSuperclass
public class CardInfo implements Serializable{


	@NotNull
	@Column(name="card_number")
	private String cardNumber;
	
	@NotNull
	@Column(name="card_type")
	private String cardType;
	
	@NotNull
	@Column(name = "card_expire_date")
	private String cardExpireDate;
	
	@NotNull
	@Column(name = "bank_name")
	private String bankName;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardExpireDate() {
		return cardExpireDate;
	}

	public void setCardExpireDate(String cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
	
	

}
