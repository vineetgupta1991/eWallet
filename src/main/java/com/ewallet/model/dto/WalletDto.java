package com.ewallet.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class WalletDto {

	private int walletId;

	private List<AccountDto> accountsInWallet;

	private CustomerDto walletOfCustomer;
}
