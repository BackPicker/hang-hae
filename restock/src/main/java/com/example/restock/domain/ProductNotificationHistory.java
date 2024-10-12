package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 상품별 재입고 알림 히스토리
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_notification_history")
public class ProductNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 히스토리의 고유 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 관련된 상품

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; // 알림 전송 상태

    @Column(updatable = false)
    private LocalDateTime createdAt;// 생성 시간
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
