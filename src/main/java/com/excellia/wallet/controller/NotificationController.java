package com.excellia.wallet.controller;

import com.excellia.wallet.entity.Notification;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.NotificationService;
import com.excellia.wallet.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<Notification> getNotifications() {
        return notificationService.getUserNotifications(getCurrentUser());
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable UUID id) {
        notificationService.markAsRead(id, getCurrentUser());
    }
}