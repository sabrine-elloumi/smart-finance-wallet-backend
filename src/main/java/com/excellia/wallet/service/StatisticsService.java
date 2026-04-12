package com.excellia.wallet.service;

import com.excellia.wallet.dto.StatisticsDto;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final TransactionRepository transactionRepository;

    public StatisticsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public StatisticsDto getMonthlyStatistics(User user, YearMonth month) {
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);

        List<Object[]> categoryTotals = transactionRepository.findSumByCategoryAndUserAndDateRange(user, start, end);
        Map<String, BigDecimal> expensesByCategory = new HashMap<>();
        for (Object[] row : categoryTotals) {
            String catName = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            expensesByCategory.put(catName, amount);
        }

        BigDecimal totalExpenses = transactionRepository.sumByTypeAndUserAndDateRange(user, "DEPENSE", start, end);
        BigDecimal totalIncome = transactionRepository.sumByTypeAndUserAndDateRange(user, "REVENU", start, end);

        StatisticsDto dto = new StatisticsDto();
        dto.setTotalExpenses(totalExpenses != null ? totalExpenses : BigDecimal.ZERO);
        dto.setTotalIncome(totalIncome != null ? totalIncome : BigDecimal.ZERO);
        dto.setExpensesByCategory(expensesByCategory);
        return dto;
    }
}
