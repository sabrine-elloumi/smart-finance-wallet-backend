package com.excellia.wallet.controller;

import com.excellia.wallet.dto.DashboardDto;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.DashboardService;
import com.excellia.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;

    public DashboardController(DashboardService dashboardService, UserService userService) {
        this.dashboardService = dashboardService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public DashboardDto getDashboard(@RequestParam(required = false) String month) {
        YearMonth currentMonth = month != null ? YearMonth.parse(month) : YearMonth.now();
        return dashboardService.getDashboard(getCurrentUser(), currentMonth);
    }
}