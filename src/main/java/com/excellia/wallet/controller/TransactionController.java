package com.excellia.wallet.controller;

import com.excellia.wallet.dto.TransactionDto;
import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.TransactionService;
import com.excellia.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getUserTransactions(getCurrentUser());
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody TransactionDto dto) {
        return transactionService.addTransaction(
            getCurrentUser(),
            dto.getAmount(),
            dto.getDescription(),
            dto.getCategoryId(),
            dto.getType()
        );
    }
}