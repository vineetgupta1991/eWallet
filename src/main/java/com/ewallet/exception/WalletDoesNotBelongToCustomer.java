package com.ewallet.exception;


import com.ewallet.model.entity.*;

public class WalletDoesNotBelongToCustomer extends Exception {
	public WalletDoesNotBelongToCustomer(Customer customer, Wallet wallet) {
		super("Customer with id" + customer.getUserId() + " does not have associated walletId : " + wallet.getWalletId());
	}
}
