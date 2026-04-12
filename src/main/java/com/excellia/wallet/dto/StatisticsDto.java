package com.excellia.wallet.dto;

import java.math.BigDecimal;
import java.util.Map;

public class StatisticsDto {
    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private Map<String, BigDecimal> expensesByCategory;
    private Map<Integer, BigDecimal> monthlyTrend;
    private BigDecimal averageExpense;
    private int transactionCount;

    // Getters et setters
    public BigDecimal getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(BigDecimal totalExpenses) { this.totalExpenses = totalExpenses; }

    public BigDecimal getTotalIncome() { return totalIncome; }
    public void setTotalIncome(BigDecimal totalIncome) { this.totalIncome = totalIncome; }

    public Map<String, BigDecimal> getExpensesByCategory() { return expensesByCategory; }
    public void setExpensesByCategory(Map<String, BigDecimal> expensesByCategory) { this.expensesByCategory = expensesByCategory; }

    public Map<Integer, BigDecimal> getMonthlyTrend() { return monthlyTrend; }
    public void setMonthlyTrend(Map<Integer, BigDecimal> monthlyTrend) { this.monthlyTrend = monthlyTrend; }

    public BigDecimal getAverageExpense() { return averageExpense; }
    public void setAverageExpense(BigDecimal averageExpense) { this.averageExpense = averageExpense; }

    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }
}
