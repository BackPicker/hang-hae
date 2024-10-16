package com.hanghae.project.domain.notification.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record ProductNotificationHistory(
    long id,
    long productId,
    long restockRound,
    @Nullable Long lastSentUserId,
    @NotNull String state
) {
    // 의도적으로 enum 으로 관리하지 않음.
    private static final String STATE_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATE_CANCELED_BY_SOLD_OUT = "CANCELED_BY_SOLD_OUT";
    private static final String STATE_CANCELED_BY_ERROR = "CANCELED_BY_ERROR";
    private static final String STATE_COMPLETED = "COMPLETED";

    public static ProductNotificationHistory inProgress(
        long productId,
        long restockRound
    ) {
        return new ProductNotificationHistory(0L, productId, restockRound, null, STATE_IN_PROGRESS);
    }

    public static ProductNotificationHistory canceledBySoldOut(
        long id,
        long productId,
        long restockRound,
        @Nullable Long lastSentUserId
    ) {
        return new ProductNotificationHistory(id, productId, restockRound, lastSentUserId, STATE_CANCELED_BY_SOLD_OUT);
    }

    public static ProductNotificationHistory canceledByError(
        long id,
        long productId,
        long restockRound,
        @Nullable Long lastSentUserId
    ) {
        return new ProductNotificationHistory(id, productId, restockRound, lastSentUserId, STATE_CANCELED_BY_ERROR);
    }

    public ProductNotificationHistory completed(@Nullable Long lastSentUserId) {
        return new ProductNotificationHistory(id, productId, restockRound, lastSentUserId, STATE_COMPLETED);
    }

    public boolean isManualTriggerEnabled() {
        return state.equals(STATE_CANCELED_BY_ERROR);
    }
}
