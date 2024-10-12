package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_user_notification")
public class ProductUserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 알림을 설정한 상품

    private Long userId; // 알림을 설정한 사용자의 ID

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 시간

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 현재 시간으로 설정
        updatedAt = LocalDateTime.now(); // 현재 시간으로 설정
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // 현재 시간으로 설정
    }
}

