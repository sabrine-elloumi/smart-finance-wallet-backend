package com.excellia.wallet.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class CategoryDto {
    private UUID id;
    @NotBlank
    private String name;
    private String icon;
    private String color;
    private boolean isSystem;

    // getters/setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean isSystem() { return isSystem; }
    public void setSystem(boolean system) { isSystem = system; }
}
