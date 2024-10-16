package com.hanghae.project.domain.notification.user;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ProductUserNotificationHistory(
    long productId,
    long userId,
    long restockRound,
    @NotNull LocalDateTime sentAt
) {
    public static ProductUserNotificationHistory sent(long productId, long userId, long restockRound) {
        return new ProductUserNotificationHistory(productId, userId, restockRound, LocalDateTime.now());
    }
}
