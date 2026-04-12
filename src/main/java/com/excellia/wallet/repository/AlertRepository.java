// AlertRepository.java
package com.excellia.wallet.repository;

import com.excellia.wallet.entity.Notification;
import com.excellia.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserOrderBySentAtDesc(User user);
    List<Notification> findByUserAndIsRead(User user, boolean isRead);
}