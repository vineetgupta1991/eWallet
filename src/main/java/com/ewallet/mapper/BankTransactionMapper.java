package com.ewallet.mapper;

import com.ewallet.model.dto.BankTransactionDto;
import com.ewallet.model.entity.BankTransaction;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BankTransactionMapper {

	BankTransactionDto bankTransactionToBankTransactionDto(BankTransaction bankTransaction);

	default List<BankTransactionDto> bankTransactionToBankTransactionDto(List<BankTransaction> bankTransactions) {
		return bankTransactions.stream()
			.map(this::bankTransactionToBankTransactionDto)
			.collect(Collectors.toList());

	}
}
