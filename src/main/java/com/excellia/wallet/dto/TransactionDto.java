package com.excellia.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    private BigDecimal amount;
    private String description;
    private UUID categoryId;        // remplace l'ancien String category
    private String type;            // DEPENSE ou REVENU
    private LocalDateTime transactionDate;

    // Constructeurs
    public TransactionDto() {}

    public TransactionDto(BigDecimal amount, String description, UUID categoryId, String type, LocalDateTime transactionDate) {
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
        this.type = type;
        this.transactionDate = transactionDate;
    }

    // Getters et Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
}