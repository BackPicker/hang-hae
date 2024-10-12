package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 상품 + 유저별 알림 히스토리
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_user_notification_history")
public class ProductUserNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 알림을 받은 상품

    private Long userId; // 알림을 받은 사용자의 ID

    @Enumerated(EnumType.STRING)
    private NotificationResult notificationStatus; // 알림 전송 결과

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


}
