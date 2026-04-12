package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Budget;
import com.excellia.wallet.entity.Category;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    List<Budget> findByUserAndMonth(User user, YearMonth month);

    Optional<Budget> findByUserAndCategoryAndMonth(User user, Category category, YearMonth month);

    List<Budget> findByUserAndMonthOrderByCategoryAsc(User user, YearMonth month);
}