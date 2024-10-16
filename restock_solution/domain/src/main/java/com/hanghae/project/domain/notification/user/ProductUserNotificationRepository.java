package com.hanghae.project.domain.notification.user;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ProductUserNotificationRepository {

    @NotNull
    List<ProductUserNotification> getActiveProductUserNotifications(long productId, @NotNull String cursor, int size);
}
