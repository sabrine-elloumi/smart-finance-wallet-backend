package com.excellia.wallet.service;

import com.excellia.wallet.entity.RecurringPayment;
import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.RecurringPaymentRepository;
import com.excellia.wallet.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecurringPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(RecurringPaymentService.class);
    private final TransactionRepository transactionRepository;
    private final RecurringPaymentRepository recurringPaymentRepository;

    public RecurringPaymentService(TransactionRepository transactionRepository,
                                   RecurringPaymentRepository recurringPaymentRepository) {
        this.transactionRepository = transactionRepository;
        this.recurringPaymentRepository = recurringPaymentRepository;
    }

    public void detectAndSaveRecurringPayments() {
        logger.info("Début de la détection des paiements récurrents");
        List<User> users = transactionRepository.findAllUsersWithTransactions();
        for (User user : users) {
            detectForUser(user);
        }
        logger.info("Détection terminée");
    }

    private void detectForUser(User user) {
        List<Transaction> transactions = transactionRepository.findByUserOrderByTransactionDateDesc(user);
        // Filtrer les dépenses uniquement
        Map<String, List<Transaction>> byMerchant = transactions.stream()
                .filter(t -> "DEPENSE".equals(t.getType()))
                .collect(Collectors.groupingBy(Transaction::getDescription));

        for (Map.Entry<String, List<Transaction>> entry : byMerchant.entrySet()) {
            List<Transaction> txns = entry.getValue();
            if (txns.size() >= 3) {
                List<LocalDate> dates = txns.stream()
                        .map(t -> t.getTransactionDate().toLocalDate())
                        .sorted()
                        .collect(Collectors.toList());

                long avgDays = ChronoUnit.DAYS.between(dates.get(0), dates.get(dates.size()-1)) / (dates.size()-1);
                if (avgDays >= 25 && avgDays <= 35) {
                    BigDecimal avgAmount = txns.stream()
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(BigDecimal.valueOf(txns.size()), 2, RoundingMode.HALF_UP);

                    RecurringPayment recurring = new RecurringPayment();
                    recurring.setUser(user);
                    recurring.setMerchant(entry.getKey());
                    recurring.setAverageAmount(avgAmount);
                    recurring.setFrequencyDays((int) avgDays);
                    recurring.setNextExpectedDate(dates.get(dates.size()-1).plusDays(avgDays));
                    recurring.setCategory(txns.get(0).getCategory());
                    recurringPaymentRepository.save(recurring);
                }
            }
        }
    }
}
