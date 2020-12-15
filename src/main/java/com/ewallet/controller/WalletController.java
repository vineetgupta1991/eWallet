package com.ewallet.controller;

import com.ewallet.exception.*;
import com.ewallet.model.dto.*;
import com.ewallet.model.enums.TransactionType;
import com.ewallet.service.WalletService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Wallet")
@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@ApiOperation(value = "Create Wallet")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/api/wallet/{customerId}")
	public WalletDto createWallet(@PathVariable("customerId") int customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException {
		return walletService.createWallet(customerId);
	}

	@ApiOperation(value = "Get account balance")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/api/wallet/{walletId}/account/{accountId}/balance")
	public Float getAccountBalance(
		@PathVariable("walletId") int walletId,
		@PathVariable("accountId") int accountId) throws AccountNotAssociatedWithWalletException, WalletIdDoesNotExistException {
		return walletService.getAccountBalanceForCurrentWallet(walletId, accountId);
	}

	@ApiOperation(value = "Withdraw amount")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/api/wallet/{walletId}/account/{accountId}/withdraw/{amount}")
	public AccountDto withdraw(
		@PathVariable("walletId") int walletId,
		@PathVariable("accountId") int accountId,
		@PathVariable("amount") float amount) throws InsufficientBalanceInWalletException, AccountNotAssociatedWithWalletException, WalletIdDoesNotExistException {
		return walletService.withdrawFromAccount(walletId, accountId, amount, TransactionType.WITHDRAW);

	}

	@ApiOperation(value = "Deposit amount")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/api/wallet/{walletId}/account/{accountId}/deposit/{amount}")
	public AccountDto deposit(@PathVariable("walletId") int walletId,
	                          @PathVariable("accountId") int accountId,
	                          @PathVariable("amount") float amount) throws AccountNotAssociatedWithWalletException, WalletIdDoesNotExistException {
		return walletService.depositToAccount(walletId, accountId, amount, TransactionType.DEPOSIT);

	}

	@ApiOperation(value = "Transfer amount")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/api/wallet/{fromWalletId}/account/{fromAccountId}/transfer/wallet/{toWalletId}/account/{toAccountId}/amount/{amount}")
	public void transfer(@PathVariable("fromWalletId") int fromWalletId,
	                     @PathVariable("fromAccountId") int fromAccountId,
	                     @PathVariable("toWalletId") int toWalletId,
	                     @PathVariable("toAccountId") int toAccountId,
	                     @PathVariable("amount") float amount) throws WalletIdDoesNotExistException,
		AccountNotAssociatedWithWalletException, InsufficientBalanceInWalletException {
		walletService.transferToAccount(fromWalletId, fromAccountId, toWalletId, toAccountId, amount);

	}

	@ApiOperation(value = "Statement")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping("/api/wallet/{walletId}/account/{accountId}/lastNTransactions/{n}")
	public List<BankTransactionDto> getStatement(@PathVariable("walletId") int walletId,
	                                             @PathVariable("accountId") int accountId,
	                                             @PathVariable("n") int n) throws AccountNotAssociatedWithWalletException, WalletIdDoesNotExistException {
		return walletService.getStatement(walletId, accountId, n);

	}


}
