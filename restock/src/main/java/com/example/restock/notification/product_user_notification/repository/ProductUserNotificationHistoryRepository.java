package com.example.restock.notification.product_user_notification.repository;

import com.example.restock.notification.product_user_notification.domain.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
}
