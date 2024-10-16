package com.hanghae.project.infrastructure.notification.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity(name = "ProductUserNotificationHistory")
public class ProductUserNotificationHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    private long productId;
    private long userId;
    private long restockRound;
    @NotNull private LocalDateTime sentAt;

    protected ProductUserNotificationHistoryEntity() {}

    public ProductUserNotificationHistoryEntity(
        long productId,
        long userId,
        long restockRound,
        @NotNull LocalDateTime sentAt
    ) {
        this.id = 0L;
        this.productId = productId;
        this.userId = userId;
        this.restockRound = restockRound;
        this.sentAt = sentAt;
    }
}
