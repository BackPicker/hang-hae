package com.example.restock.repository;

import com.example.restock.domain.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {
}
