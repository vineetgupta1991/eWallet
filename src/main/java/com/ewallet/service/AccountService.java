package com.ewallet.service;

import com.ewallet.model.dto.AccountDto;
import com.ewallet.model.entity.*;
import com.ewallet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public Account createAccount(@RequestBody AccountDto account) {
		Customer customer = null;
		if (!CollectionUtils.isEmpty(customerRepository.findCustomerByEmail(account.getAccountHolder().getEmail()))) {
			customer = customerRepository.findCustomerByEmail(account.getAccountHolder().getEmail()).get(0);
		} else {
			customer = Customer.builder()
				.fname(account.getAccountHolder().getFname())
				.lname(account.getAccountHolder().getLname())
				.email(account.getAccountHolder().getEmail())
				.build();
			customerRepository.save(customer);
		}
		return accountRepository.save(Account.builder()
			.accountHolder(customer)
			.accountNumber(account.getAccountNumber())
			.balance(account.getBalance()).build());
	}
}
