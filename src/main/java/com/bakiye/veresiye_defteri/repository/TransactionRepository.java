package com.bakiye.veresiye_defteri.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bakiye.veresiye_defteri.data.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("SELECT SUM(t.transactionAmount) FROM Transaction t WHERE t.customer.id = :customerId AND t.transactionType = :type")
	BigDecimal getTotalAmountByCustomerIdAndType(Long customerId, int type);

	@Query("SELECT COALESCE(SUM(CASE WHEN t.transactionType = 0 THEN t.transactionAmount ELSE 0 END), 0) "
			+ "- COALESCE(SUM(CASE WHEN t.transactionType = 1 THEN t.transactionAmount ELSE 0 END), 0) "
			+ "FROM Transaction t WHERE t.customer.id = :customerId")
	BigDecimal getTotalDebtByCustomerId(Long customerId);

	@Query("SELECT MAX(transactionDate) FROM Transaction WHERE customer.id = :customerId")
	LocalDate getLastTransactionDateByCustomerId(Long customerId);

}
