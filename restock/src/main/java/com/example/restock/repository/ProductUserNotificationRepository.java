package com.example.restock.repository;

import com.example.restock.domain.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {


    @Query("SELECT pun FROM ProductUserNotification pun join fetch pun.product p WHERE p.id = :productId")
    List<ProductUserNotification> findByProductId(@Param("productId") Long productId);
}

