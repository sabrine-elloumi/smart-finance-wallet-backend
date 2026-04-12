package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Category;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByIsSystemTrue();                      // catégories système
    List<Category> findByUserOrIsSystemTrue(User user);       // catégories de l'utilisateur + système
    Optional<Category> findByNameAndUser(String name, User user);
}