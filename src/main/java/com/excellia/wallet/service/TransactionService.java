package com.excellia.wallet.service;

import com.excellia.wallet.entity.Category;
import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.CategoryRepository;
import com.excellia.wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AiClientService aiClientService;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryRepository categoryRepository,
                              AiClientService aiClientService) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.aiClientService = aiClientService;
    }

    public Transaction addTransaction(User user, BigDecimal amount, String description,
                                      UUID categoryId, String type) {
        Category category;
        Double confidence = null;

        // Si l'utilisateur n'a pas fourni de catégorie, on utilise l'IA
        if (categoryId == null) {
            // Appel à l'IA (simulation pour l'instant)
            AiClientService.AiResponse aiResponse = aiClientService.simulateCategorize(description);
            String predictedCategoryName = aiResponse.getCategory();
            confidence = aiResponse.getConfidence();

            // Chercher la catégorie par son nom (catégorie système)
            category = categoryRepository.findByNameAndUser(predictedCategoryName, null)
                    .orElseGet(() -> categoryRepository.findByNameAndUser("Autre", null)
                            .orElseThrow(() -> new RuntimeException("Catégorie par défaut 'Autre' introuvable")));
        } else {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCategory(category);
        transaction.setType(type);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAiConfidence(confidence != null ? confidence : 0.95);
        transaction.setIsAnomaly(false); // à implémenter plus tard

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUserOrderByTransactionDateDesc(user);
    }

    // Ajoutez ici d'autres méthodes (update, delete, getById) si nécessaire
}