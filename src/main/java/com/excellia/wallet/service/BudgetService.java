package com.excellia.wallet.service;

import com.excellia.wallet.entity.Budget;
import com.excellia.wallet.entity.Category;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.BudgetRepository;
import com.excellia.wallet.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetService(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public Budget setBudget(User user, UUID categoryId, BigDecimal amount, YearMonth month, String period) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        Budget budget = budgetRepository.findByUserAndCategoryAndBudgetMonth(user, category, month)
                .orElse(new Budget());

        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(amount);
        budget.setBudgetMonth(month);
        if (period != null) {
            budget.setPeriod(period);
        }

        return budgetRepository.save(budget);
    }

    public List<Budget> getUserBudgets(User user, YearMonth month) {
        return budgetRepository.findByUserAndBudgetMonthOrderByCategoryAsc(user, month);
    }

    public void updateSpentAmount(User user, Category category, YearMonth month, BigDecimal additionalSpent) {
        Budget budget = budgetRepository.findByUserAndCategoryAndBudgetMonth(user, category, month).orElse(null);
        if (budget != null) {
            BigDecimal newSpent = budget.getSpent().add(additionalSpent);
            budget.setSpent(newSpent);
            budgetRepository.save(budget);
        }
    }
}