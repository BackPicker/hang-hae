package com.example.restock.repository;

import com.example.restock.domain.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {
}
