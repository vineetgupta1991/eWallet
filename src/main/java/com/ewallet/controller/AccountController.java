package com.ewallet.controller;

import com.ewallet.model.dto.AccountDto;
import com.ewallet.model.entity.Account;
import com.ewallet.service.AccountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@ApiOperation(value = "Create Account")
	@ApiImplicitParam(name = "language", dataType = "string", paramType = "header", value = "", defaultValue = "eng")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Validation Failed"),
		@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@PostMapping("/api/account")
	public Account createAccount(@RequestBody AccountDto account) {
		return accountService.createAccount(account);
	}

}
