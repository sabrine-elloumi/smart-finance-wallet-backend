package com.excellia.wallet.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public class BudgetRequest {
    private String category;
    private BigDecimal amount;
    private YearMonth month;
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public YearMonth getMonth() { return month; }
    public void setMonth(YearMonth month) { this.month = month; }
}