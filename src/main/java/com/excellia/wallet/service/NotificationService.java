package com.excellia.wallet.service;

import com.excellia.wallet.entity.Notification;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(User user, String type, String title, String body) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setTitle(title);
        notification.setBody(body);
        notification.setRead(false);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByUserOrderBySentAtDesc(user);
    }

    public void markAsRead(UUID notificationId, User user) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        if (notification.getUser().getId().equals(user.getId())) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    public void deleteNotification(UUID notificationId, User user) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        if (notification.getUser().getId().equals(user.getId())) {
            notificationRepository.delete(notification);
        }
    }
}
