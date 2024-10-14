package com.example.restock.notification.product_user_notification.repository;

import com.example.restock.notification.product_notification.domain.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {

}
