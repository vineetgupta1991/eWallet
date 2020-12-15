package com.ewallet.mapper;

import com.ewallet.model.dto.WalletDto;
import com.ewallet.model.entity.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

	WalletDto walletToWalletDto(Wallet wallet);
}
