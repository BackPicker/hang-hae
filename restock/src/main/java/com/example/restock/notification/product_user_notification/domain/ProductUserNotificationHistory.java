package com.example.restock.notification.product_user_notification.domain;

import com.example.restock.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductUserNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long userId;  // 유저 ID

    @Column(nullable = false)
    private LocalDateTime notifiedAt = LocalDateTime.now();  // 알림 전송 시간

    public ProductUserNotificationHistory(Product product, Long userId) {
        this.product = product;
        this.userId  = userId;
    }
}
