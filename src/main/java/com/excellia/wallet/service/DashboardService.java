package com.excellia.wallet.service;

import com.excellia.wallet.dto.DashboardDto;
import com.excellia.wallet.dto.RecommendationDto;
import com.excellia.wallet.entity.Transaction;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final RecommendationService recommendationService;
    private final BudgetService budgetService;

    public DashboardService(TransactionRepository transactionRepository,
                            RecommendationService recommendationService,
                            BudgetService budgetService) {
        this.transactionRepository = transactionRepository;
        this.recommendationService = recommendationService;
        this.budgetService = budgetService;
    }

    public DashboardDto getDashboard(User user, YearMonth month) {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);

        BigDecimal totalIncome = transactionRepository.sumByTypeAndUserAndDateRange(user, "REVENU", start, end);
        BigDecimal totalExpense = transactionRepository.sumByTypeAndUserAndDateRange(user, "DEPENSE", start, end);
        BigDecimal balance = (totalIncome != null ? totalIncome : BigDecimal.ZERO)
                .subtract(totalExpense != null ? totalExpense : BigDecimal.ZERO);

        List<Transaction> recentTransactions = transactionRepository.findTop5ByUserOrderByTransactionDateDesc(user);

        List<RecommendationDto> recos = recommendationService.getUserRecommendations(user)
                .stream().map(r -> {
                    RecommendationDto dto = new RecommendationDto();
                    dto.setId(r.getId());
                    dto.setTitle(r.getTitle());
                    dto.setMessage(r.getMessage());
                    dto.setPriority(r.getPriority());
                    return dto;
                }).collect(Collectors.toList());

        DashboardDto dto = new DashboardDto();
        dto.setTotalBalance(balance);
        dto.setTotalIncome(totalIncome != null ? totalIncome : BigDecimal.ZERO);
        dto.setTotalExpense(totalExpense != null ? totalExpense : BigDecimal.ZERO);
        dto.setRecentTransactions(recentTransactions);
        dto.setRecommendations(recos);
        return dto;
    }
}
