package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
}