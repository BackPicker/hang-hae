package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_user_notification_history")
public class ProductUserNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 식별자

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 알림을 받은 상품

    private Long userId; // 알림을 받은 사용자의 ID

    @Enumerated(EnumType.STRING)
    private NotificationResult notificationStatus; // 알림 전송 결과

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 시간

    // 엔티티가 처음 생성될 때 호출
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 현재 시간으로 설정
        updatedAt = LocalDateTime.now(); // 현재 시간으로 설정
    }

    // 엔티티가 수정될 때 호출
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // 현재 시간으로 설정
    }

}
