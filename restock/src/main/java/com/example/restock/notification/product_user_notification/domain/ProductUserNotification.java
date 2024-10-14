package com.example.restock.notification.product_user_notification.domain;

import com.example.restock.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductUserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long userId;  // 유저 ID

    @Setter
    @Enumerated(EnumType.STRING)
    private ProductUserNotificationStatus userNotificationStatus;



    public ProductUserNotification(Product product, Long userId, ProductUserNotificationStatus userNotificationStatus) {
        this.product                = product;
        this.userId                 = userId;
        this.userNotificationStatus = userNotificationStatus;
    }
}
