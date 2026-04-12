package com.excellia.wallet.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;      // optionnel, ex: "🍔"
    private String color;     // optionnel, ex: "#FF5733"

    @Column(name = "is_system")
    private boolean isSystem = true;  // true = catégorie par défaut, false = créée par utilisateur

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;        // null si catégorie système

    // Constructeurs
    public Category() {}

    public Category(String name, boolean isSystem) {
        this.name = name;
        this.isSystem = isSystem;
    }

    // Getters et Setters (générés par ton IDE ou écrits à la main)
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

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}