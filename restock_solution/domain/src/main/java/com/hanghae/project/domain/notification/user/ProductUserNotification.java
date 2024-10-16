package com.hanghae.project.domain.notification.user;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ProductUserNotification(
    long id,
    long productId,
    long userId,
    @NotNull LocalDateTime createdAt
) {
}
