package com.excellia.wallet.controller;

import com.excellia.wallet.dto.BudgetRequest;
import com.excellia.wallet.entity.Budget;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.BudgetService;
import com.excellia.wallet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    
    private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);
    
    private final BudgetService budgetService;
    private final UserService userService;
    
    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }
    
    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Getting current user with phone number: {}", phoneNumber);
        return userService.findByPhoneNumber(phoneNumber);
    }
    
    @GetMapping
    public List<Budget> getBudgets(@RequestParam(required = false) String month) {
        YearMonth currentMonth = month != null ? YearMonth.parse(month) : YearMonth.now();
        logger.info("Fetching budgets for month: {}", currentMonth);
        return budgetService.getUserBudgets(getCurrentUser(), currentMonth);
    }
    
    @PostMapping
    public Budget setBudget(@RequestBody BudgetRequest request) {
        YearMonth month = request.getMonth() != null ? request.getMonth() : YearMonth.now();
        logger.info("Setting budget: category={}, amount={}, month={}", request.getCategory(), request.getAmount(), month);
        return budgetService.setBudget(getCurrentUser(), request.getCategory(), request.getAmount(), month);
    }
}