package com.ewallet.repository;

import com.ewallet.model.entity.BankTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface BankTansactionRepository extends JpaRepository<BankTransaction, Integer> {
	@Query("SELECT t FROM BankTransaction t WHERE t.transactionId=:transactionId")
	Iterable<BankTransaction> findBankTransactionById(@Param("transactionId") Integer transactionId);
}
