package com.ewallet.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BankTransactionDto {

	private int transactionId;
	private String type;
	private Date timestamp;
	private float amount;
	private float postBalance;
	private String description;

	private AccountDto transactionFromAccount;

}
