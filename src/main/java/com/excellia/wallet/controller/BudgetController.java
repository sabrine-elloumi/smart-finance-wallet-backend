package com.excellia.wallet.controller;

import com.excellia.wallet.dto.BudgetRequest;
import com.excellia.wallet.entity.Budget;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.BudgetService;
import com.excellia.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<Budget> getBudgets(@RequestParam(required = false) String month) {
        YearMonth currentMonth = month != null ? YearMonth.parse(month) : YearMonth.now();
        return budgetService.getUserBudgets(getCurrentUser(), currentMonth);
    }

    @PostMapping
    public Budget setBudget(@RequestBody BudgetRequest request) {
        YearMonth month = request.getMonth() != null ? request.getMonth() : YearMonth.now();
        return budgetService.setBudget(
            getCurrentUser(),
            request.getCategoryId(),
            request.getAmount(),
            month,
            request.getPeriod()
        );
    }
}