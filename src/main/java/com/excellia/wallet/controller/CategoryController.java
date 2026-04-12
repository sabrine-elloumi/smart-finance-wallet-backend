package com.excellia.wallet.controller;

import com.excellia.wallet.entity.Category;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.CategoryService;
import com.excellia.wallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategoriesForUser(getCurrentUser());
    }

    @PostMapping
    public Category createCategory(@RequestBody CategoryDto dto) {
        return categoryService.createCustomCategory(getCurrentUser(), dto.getName(), dto.getIcon(), dto.getColor());
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody CategoryDto dto) {
        return categoryService.updateCategory(id, dto.getName(), dto.getIcon(), dto.getColor(), getCurrentUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id, getCurrentUser());
        return ResponseEntity.ok().build();
    }

    // DTO interne
    static class CategoryDto {
        private String name;
        private String icon;
        private String color;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
}
