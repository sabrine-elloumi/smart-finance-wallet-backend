package com.excellia.wallet.controller;

import com.excellia.wallet.dto.TransactionDto;
import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.TransactionService;
import com.excellia.wallet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    
    private final TransactionService transactionService;
    private final UserService userService;
    
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }
    
    private User getCurrentUser() {
        // Le SecurityContext contient le phoneNumber (pas l'UUID)
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Getting current user with phone number: {}", phoneNumber);
        
        // Chercher par phoneNumber, PAS par findById !
        return userService.findByPhoneNumber(phoneNumber);
    }
    
    @GetMapping
    public List<Transaction> getTransactions() {
        logger.info("Fetching all transactions");
        return transactionService.getUserTransactions(getCurrentUser());
    }
    
    @PostMapping
    public Transaction addTransaction(@RequestBody TransactionDto dto) {
        logger.info("Adding transaction: amount={}, description={}", dto.getAmount(), dto.getDescription());
        return transactionService.addTransaction(
            getCurrentUser(),
            dto.getAmount(),
            dto.getDescription(),
            dto.getCategory(),
            dto.getType()
        );
    }
}