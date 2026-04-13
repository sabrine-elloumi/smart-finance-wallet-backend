package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    // ⚠️ SUPPRIME cette méthode si elle existe (problème de type)
    // List<Transaction> findByUserAndCategory(User user, String category);

    List<Transaction> findTop5ByUserOrderByTransactionDateDesc(User user);

    @Query("SELECT t.category.name, SUM(t.amount) FROM Transaction t " +
           "WHERE t.user = :user AND t.transactionDate BETWEEN :start AND :end AND t.type = 'DEPENSE' " +
           "GROUP BY t.category.name")
    List<Object[]> findSumByCategoryAndUserAndDateRange(@Param("user") User user,
                                                        @Param("start") LocalDateTime start,
                                                        @Param("end") LocalDateTime end);

    @Query("SELECT SUM(t.amount) FROM Transaction t " +
           "WHERE t.user = :user AND t.type = :type AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumByTypeAndUserAndDateRange(@Param("user") User user,
                                            @Param("type") String type,
                                            @Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);

    @Query("SELECT DISTINCT t.user FROM Transaction t")
    List<User> findAllUsersWithTransactions();
}
