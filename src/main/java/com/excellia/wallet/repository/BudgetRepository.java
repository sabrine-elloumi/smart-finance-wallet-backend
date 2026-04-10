package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Budget;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, String> {
    List<Budget> findByUserAndMonth(User user, YearMonth month);
    Optional<Budget> findByUserAndCategoryAndMonth(User user, String category, YearMonth month);
}