package com.ewallet.service;

import com.ewallet.constant.Description;
import com.ewallet.exception.*;
import com.ewallet.mapper.*;
import com.ewallet.model.dto.*;
import com.ewallet.model.entity.*;
import com.ewallet.model.enums.TransactionType;
import com.ewallet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankTansactionRepository bankTansactionRepository;

	@Autowired
	private WalletMapper walletMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private BankTransactionMapper bankTransactionMapper;

	public WalletDto createWallet(Integer customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException {

		Customer customer = customerRepository
			.findById(customerId).orElseThrow(() -> new CustomerDoesNotExistException(customerId));

		if (Objects.nonNull(customer.getWallet())) {
			throw new CustomerAlreadyHasWalletException(customer);
		}

		Wallet wallet = Wallet.builder()
			.walletOfCustomer(customer)
			.accountsInWallet(!CollectionUtils.isEmpty(customer.getCustomerAccounts()) ? new ArrayList<>(customer.getCustomerAccounts()) : null)
			.build();


		return walletMapper.walletToWalletDto(walletRepository.save(wallet));
	}

	public Float getAccountBalanceForCurrentWallet(Integer walletId, Integer accountId) throws WalletIdDoesNotExistException, AccountNotAssociatedWithWalletException {

		return getAccount(walletId, accountId).getBalance();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public AccountDto withdrawFromAccount(Integer walletId, Integer accountId, Float amount, TransactionType transactionType) throws WalletIdDoesNotExistException,
		AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

		Account associateAccount = getAccount(walletId, accountId);

		float currentBalance = associateAccount.getBalance();

		if (currentBalance < amount) {
			throw new InsufficientBalanceInWalletException(walletId);
		}
		associateAccount.setBalance(currentBalance - amount);

		Account account = accountRepository.save(associateAccount);

		// Make Entry in Transaction table
		if (TransactionType.WITHDRAW == transactionType) {
			makeEntryInTransaction(transactionType.toString(), amount, account.getBalance(), Description.WITHDRAW_DESCRIPTION, account);
		}
		return accountMapper.accountToAccountDto(account);
	}


	private void makeEntryInTransaction(String typeOfTransaction, float amount, float postBalance, String description, Account associatedAccount) {
		BankTransaction bankTransaction = new BankTransaction(typeOfTransaction, new Date(), amount, postBalance, description, associatedAccount);

		bankTansactionRepository.save(bankTransaction);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
	public AccountDto depositToAccount(Integer walletId, Integer accountId, Float amount, TransactionType transactionType) throws WalletIdDoesNotExistException,
		AccountNotAssociatedWithWalletException {

		Account associateAccount = getAccount(walletId, accountId);

		float currentBalance = associateAccount.getBalance();
		associateAccount.setBalance(currentBalance + amount);

		Account account = accountRepository.save(associateAccount);

		if (TransactionType.DEPOSIT == transactionType) {
			makeEntryInTransaction(transactionType.toString(), amount, account.getBalance(), Description.DEPOSIT_DESCRIPTION, account);
		}

		return accountMapper.accountToAccountDto(account);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { AccountNotAssociatedWithWalletException.class, WalletIdDoesNotExistException.class })
	public void transferToAccount(Integer fromWalletId, Integer fromAccountId, Integer toWalletId, Integer toAccountId, Float amount) throws WalletIdDoesNotExistException,
		AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {

		Wallet fromWallet = walletRepository.findById(fromWalletId).orElseThrow(() -> new WalletIdDoesNotExistException(fromWalletId));
		Wallet toWallet = walletRepository.findById(toWalletId).orElseThrow(() -> new WalletIdDoesNotExistException(toWalletId));

		List<Account> fromAssociateAccount = fromWallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == fromAccountId).collect(Collectors.toList());
		List<Account> toAssociateAccount = toWallet.getAccountsInWallet().stream().filter(aa -> aa.getAccountNumber() == toAccountId).collect(Collectors.toList());

		if (fromAssociateAccount.isEmpty()) {
			throw new AccountNotAssociatedWithWalletException(fromWalletId, fromAccountId);
		}
		if (toAssociateAccount.isEmpty()) {
			throw new AccountNotAssociatedWithWalletException(toWalletId, toAccountId);
		}

		// Withdraw
		AccountDto fromAccount = withdrawFromAccount(fromWalletId, fromAccountId, amount, TransactionType.TRANSFER);
		String withdrawDescription = "$"
		                       + amount
		                       + " transferred to accountId : "
		                       + toAccountId;
		makeEntryInTransaction(TransactionType.TRANSFER.toString(), amount, fromAccount.getBalance(), withdrawDescription, getAccount(fromWallet.getWalletId(), fromAccountId));


		// deposit
		AccountDto toAccount = depositToAccount(toWalletId, toAccountId, amount, TransactionType.TRANSFER);
		String depositDescription = "$"
		                        + amount
		                        + " transferred from accountId : "
		                        + fromAccountId;
		makeEntryInTransaction(TransactionType.TRANSFER.toString(), amount, toAccount.getBalance(), depositDescription, getAccount(toWallet.getWalletId(), toAccountId));

	}

	public List<BankTransactionDto> getStatement(Integer walletId, Integer accountId, Integer n) throws WalletIdDoesNotExistException,
		AccountNotAssociatedWithWalletException {

		Account associateAccount = getAccount(walletId, accountId);

		List<BankTransaction> bankTransactions = associateAccount.getBankTransactions();

		bankTransactions.sort((bankTransaction1, bankTransaction2) -> bankTransaction2.getTimestamp().compareTo(bankTransaction1.getTimestamp()));

		n = bankTransactions.size() >= n ? n : bankTransactions.size();

		return bankTransactionMapper.bankTransactionToBankTransactionDto(bankTransactions.subList(0, n));

	}

	private Account getAccount(Integer walletId, Integer accountId) throws WalletIdDoesNotExistException, AccountNotAssociatedWithWalletException {
		Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletIdDoesNotExistException(walletId));

		return wallet.getAccountsInWallet().stream()
			.filter(account -> account.getAccountNumber() == accountId)
			.findFirst().orElseThrow(() -> new AccountNotAssociatedWithWalletException(walletId, accountId));
	}
}
