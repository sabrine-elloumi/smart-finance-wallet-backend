package com.excellia.wallet.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public class BudgetRequest {

    private UUID categoryId;        // remplace l'ancien String category
    private BigDecimal amount;
    private YearMonth month;
    private String period;          // MONTHLY, YEARLY (optionnel)

    // Constructeurs
    public BudgetRequest() {}

    public BudgetRequest(UUID categoryId, BigDecimal amount, YearMonth month, String period) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.month = month;
        this.period = period;
    }

    // Getters et Setters
    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public YearMonth getMonth() { return month; }
    public void setMonth(YearMonth month) { this.month = month; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}