package com.excellia.wallet.service;

import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnomalyDetectionService {

    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;

    public AnomalyDetectionService(TransactionRepository transactionRepository,
                                   NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
    }

    public void detectAnomalies(Transaction newTransaction) {
        User user = newTransaction.getUser();
        
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Transaction> recentTransactions = transactionRepository
                .findByUserOrderByTransactionDateDesc(user)
                .stream()
                .filter(t -> t.getTransactionDate().isAfter(thirtyDaysAgo))
                .toList();
        
        if (!recentTransactions.isEmpty()) {
            BigDecimal averageAmount = recentTransactions.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(recentTransactions.size()), 2, RoundingMode.HALF_UP);
            
            // Si la transaction est 2x plus grande que la moyenne
            if (newTransaction.getAmount().compareTo(averageAmount.multiply(BigDecimal.valueOf(2))) > 0) {
                newTransaction.setIsAnomaly(true);
                notificationService.sendNotification(
                    user,
                    "ANOMALY",
                    "Dépense anormale détectée",
                    String.format("Transaction de %.2f TND chez %s dépasse votre moyenne habituelle", 
                                  newTransaction.getAmount(), newTransaction.getDescription())
                );
            }
        }
    }
}
