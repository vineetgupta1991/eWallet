package com.ewallet.repository;

import com.ewallet.model.entity.Wallet;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	@Query("SELECT w FROM Wallet  w WHERE w.walletId=:walletId")
	Iterable<Wallet> findWalletById(@Param("walletId") Integer walletId);
}
