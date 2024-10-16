package com.hanghae.project.infrastructure.notification.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity(name = "ProductUserNotification")
public class ProductUserNotificationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private long productId;
    private long userId;
    private @NotNull LocalDateTime createdAt;

    protected ProductUserNotificationEntity() {}

    public Long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getUserId() {
        return userId;
    }

    @NotNull
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
