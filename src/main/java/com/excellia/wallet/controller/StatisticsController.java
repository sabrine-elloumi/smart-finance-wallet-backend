package com.excellia.wallet.controller;

import com.excellia.wallet.dto.StatisticsDto;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.StatisticsService;
import com.excellia.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final UserService userService;

    public StatisticsController(StatisticsService statisticsService, UserService userService) {
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/monthly")
    public StatisticsDto getMonthlyStats(@RequestParam(required = false) String month) {
        YearMonth currentMonth = month != null ? YearMonth.parse(month) : YearMonth.now();
        return statisticsService.getMonthlyStatistics(getCurrentUser(), currentMonth);
    }
}