package com.example.restock.repository;

import com.example.restock.domain.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
}
