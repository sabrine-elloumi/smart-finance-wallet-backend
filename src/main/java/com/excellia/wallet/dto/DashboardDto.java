package com.excellia.wallet.dto;

import com.excellia.wallet.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardDto {
    private BigDecimal totalBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private Map<String, BigDecimal> expensesByCategory; // category -> amount
    private List<Transaction> recentTransactions;
    private Double predictedNextMonth; // prévision IA
    private List<String> alerts;
    private List<RecommendationDto> recommendations;
    private Map<String, Double> budgetProgress; // category -> pourcentage

    // getters/setters
    public BigDecimal getTotalBalance() { return totalBalance; }
    public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }
    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public void setTotalExpense(BigDecimal totalExpense) { this.totalExpense = totalExpense; }
    public Map<String, BigDecimal> getExpensesByCategory() { return expensesByCategory; }
    public void setExpensesByCategory(Map<String, BigDecimal> expensesByCategory) { this.expensesByCategory = expensesByCategory; }
    public List<Transaction> getRecentTransactions() { return recentTransactions; }
    public void setRecentTransactions(List<Transaction> recentTransactions) { this.recentTransactions = recentTransactions; }
    public Double getPredictedNextMonth() { return predictedNextMonth; }
    public void setPredictedNextMonth(Double predictedNextMonth) { this.predictedNextMonth = predictedNextMonth; }
    public List<String> getAlerts() { return alerts; }
    public void setAlerts(List<String> alerts) { this.alerts = alerts; }
    public List<RecommendationDto> getRecommendations() { return recommendations; }
    public void setRecommendations(List<RecommendationDto> recommendations) { this.recommendations = recommendations; }
    public Map<String, Double> getBudgetProgress() { return budgetProgress; }
    public void setBudgetProgress(Map<String, Double> budgetProgress) { this.budgetProgress = budgetProgress; }
}