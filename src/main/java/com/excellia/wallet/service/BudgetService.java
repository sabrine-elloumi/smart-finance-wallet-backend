package com.excellia.wallet.service;

import com.excellia.wallet.entity.Budget;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetService {
    
    private final BudgetRepository budgetRepository;
    
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    
    public Budget setBudget(User user, String category, BigDecimal amount, YearMonth month) {
        Budget budget = budgetRepository.findByUserAndCategoryAndMonth(user, category, month)
            .orElse(new Budget());
        
        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(amount);
        budget.setMonth(month);
        
        return budgetRepository.save(budget);
    }
    
    public List<Budget> getUserBudgets(User user, YearMonth month) {
        return budgetRepository.findByUserAndMonth(user, month);
    }
}