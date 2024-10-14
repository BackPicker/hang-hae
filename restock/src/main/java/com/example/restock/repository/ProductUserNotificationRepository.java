package com.example.restock.repository;

import com.example.restock.domain.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {

    List<ProductUserNotification> findAllByProductId(Long productId);


    void deleteByProductIdAndUserId(Long productId, Long userId);
}