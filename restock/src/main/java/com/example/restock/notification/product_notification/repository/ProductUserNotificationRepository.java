package com.example.restock.notification.product_notification.repository;

import com.example.restock.notification.product_user_notification.domain.ProductUserNotification;
import com.example.restock.notification.product_user_notification.domain.ProductUserNotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {


    List<ProductUserNotification> findAllByProductId(Long productId);

    List<ProductUserNotification> findAllByProductIdAndUserNotificationStatus(Long productId, ProductUserNotificationStatus status);

    void deleteByProductIdAndUserId(Long productId, Long userId);
}