package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COUNTRY")
public class Country extends IdEntity{


	@NotNull
	@Column(name = "NAME")
	private String name;
	
	@Column(name="CODE")
	private String code;

	@Column(name="INTERNET_CODE")
	private String internetCode;
	
	@Column(name="CAPITAL")
	private String capital;

	@Column(name="CURRENCY")
	private String currency;

	@Column(name="CURRENCY_CODE")
	private String currencyCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInternetCode() {
		return internetCode;
	}

	public void setInternetCode(String internetCode) {
		this.internetCode = internetCode;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
