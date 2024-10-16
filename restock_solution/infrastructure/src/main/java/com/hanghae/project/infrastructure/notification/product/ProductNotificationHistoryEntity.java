package com.hanghae.project.infrastructure.notification.product;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity(name = "ProductNotificationHistory")
public class ProductNotificationHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    private long productId;
    private long restockRound;

    @Nullable private Long lastSentUserId;
    @NotNull private String state;

    protected ProductNotificationHistoryEntity() {}

    public ProductNotificationHistoryEntity(
        long productId,
        long restockRound,
        @Nullable Long lastSentUserId,
        @NotNull String state
    ) {
        this.id = 0L;
        this.productId = productId;
        this.restockRound = restockRound;
        this.lastSentUserId = lastSentUserId;
        this.state = state;
    }

    public ProductNotificationHistoryEntity(
        long id,
        long productId,
        long restockRound,
        @Nullable Long lastSentUserId,
        @NotNull String state
    ) {
        this.id = id;
        this.productId = productId;
        this.restockRound = restockRound;
        this.lastSentUserId = lastSentUserId;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getRestockRound() {
        return restockRound;
    }

    @Nullable
    public Long getLastSentUserId() {
        return lastSentUserId;
    }

    @NotNull
    public String getState() {
        return state;
    }
}
