package com.excellia.wallet.service;

import com.excellia.wallet.entity.Category;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Récupérer toutes les catégories (système + celles de l'utilisateur)
    public List<Category> getCategoriesForUser(User user) {
        return categoryRepository.findByUserOrIsSystemTrue(user);
    }

    // Créer une catégorie personnalisée
    public Category createCustomCategory(User user, String name, String icon, String color) {
        Category category = new Category(name, false);
        category.setUser(user);
        category.setIcon(icon);
        category.setColor(color);
        return categoryRepository.save(category);
    }

    // Modifier une catégorie personnalisée
    public Category updateCategory(UUID id, String name, String icon, String color, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        if (!category.isSystem() && category.getUser().getId().equals(user.getId())) {
            category.setName(name);
            category.setIcon(icon);
            category.setColor(color);
            return categoryRepository.save(category);
        }
        throw new RuntimeException("Vous ne pouvez modifier que vos propres catégories");
    }

    // Supprimer une catégorie personnalisée
    public void deleteCategory(UUID id, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        if (!category.isSystem() && category.getUser().getId().equals(user.getId())) {
            categoryRepository.delete(category);
        } else {
            throw new RuntimeException("Suppression non autorisée");
        }
    }

    // Initialiser les catégories système (à appeler au démarrage)
    public void initSystemCategories() {
        if (categoryRepository.findByIsSystemTrue().isEmpty()) {
            String[] defaultCategories = {
                "Alimentation", "Transport", "Loisirs", "Santé", "Factures",
                "Vêtements", "Éducation", "Logement", "Voyages", "Autre"
            };
            for (String catName : defaultCategories) {
                Category cat = new Category(catName, true);
                categoryRepository.save(cat);
            }
        }
    }
}