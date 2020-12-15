package com.ewallet.exception;

import com.ewallet.model.entity.Customer;

public class CustomerAlreadyHasWalletException extends Exception {
	public CustomerAlreadyHasWalletException(Customer customer) {
		super("Customer " + customer.getFname() + " " + customer.getLname() + " already owns a wallet : " + customer.getWallet().getWalletId());
	}
}
