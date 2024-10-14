package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Setter
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;  // 상태: IN_PROGRESS, CANCELED_BY_SOLD_OUT, CANCELED_BY_ERROR, COMPLETED

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public ProductNotificationHistory(Product product, NotificationStatus status) {
        this.product = product;
        this.status  = status;
    }
}

