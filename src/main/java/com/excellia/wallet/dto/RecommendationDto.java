package com.excellia.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecommendationDto {
    private String id;
    private String type;
    private String title;
    private String message;
    private BigDecimal potentialSavings;
    private String priority;
    private String status;
    private LocalDateTime createdAt;

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public BigDecimal getPotentialSavings() { return potentialSavings; }
    public void setPotentialSavings(BigDecimal potentialSavings) { this.potentialSavings = potentialSavings; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}