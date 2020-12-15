package com.ewallet.mapper;

import com.ewallet.model.dto.AccountDto;
import com.ewallet.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

	AccountDto accountToAccountDto(Account account);
}
