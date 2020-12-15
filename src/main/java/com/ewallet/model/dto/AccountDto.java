package com.ewallet.model.dto;

import lombok.Data;

@Data
public class AccountDto {

	private int accountNumber;
	private float balance;

	private CustomerDto accountHolder;
}
