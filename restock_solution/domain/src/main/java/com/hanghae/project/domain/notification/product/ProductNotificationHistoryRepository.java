package com.hanghae.project.domain.notification.product;

import jakarta.annotation.Nullable;

public interface ProductNotificationHistoryRepository {

    @Nullable
    ProductNotificationHistory findLatestHistory(long productId);

    @Nullable
    ProductNotificationHistory findByProductIdAndRestockRound(long productId, long restockRound);

    void save(ProductNotificationHistory history);
}
