package com.sophos.poc.wsrestcomprar.model;

import java.util.Date;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class PagarCompraReq {
	@JsonProperty("id_trn")
	private String id_trn = null;

	@JsonProperty("account_id")
	private String account_id = null;

	@JsonProperty("account_type")
	private String account_type = null;

	@JsonProperty("account_id_to")
	private String account_id_to = null;

	@JsonProperty("account_type_to")
	private String account_type_to = null;

	@JsonProperty("ammount")
	private String ammount = null;

	@JsonProperty("currency")
	private String currency = null;

	@JsonProperty("card_id")
	private Double card_id = null;

	@JsonProperty("pin")
	private String pin = null;

	@JsonProperty("date")
	private Date date = null;

	public String getId_trn() {
		return id_trn;
	}

	public void setId_trn(String id_trn) {
		this.id_trn = id_trn;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getAccount_id_to() {
		return account_id_to;
	}

	public void setAccount_id_to(String account_id_to) {
		this.account_id_to = account_id_to;
	}

	public String getAccount_type_to() {
		return account_type_to;
	}

	public void setAccount_type_to(String account_type_to) {
		this.account_type_to = account_type_to;
	}

	public String getAmmount() {
		return ammount;
	}

	public void setAmmount(String ammount) {
		this.ammount = ammount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCard_id() {
		return card_id;
	}

	public void setCard_id(Double card_id) {
		this.card_id = card_id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
