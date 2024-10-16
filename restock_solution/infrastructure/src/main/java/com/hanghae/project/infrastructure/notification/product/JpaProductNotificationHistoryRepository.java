package com.hanghae.project.infrastructure.notification.product;

import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistoryEntity, Long> {

    @Query("""
        SELECT p FROM ProductNotificationHistory p
        WHERE 1=1
        AND p.productId=:productId
    """)
    List<ProductNotificationHistoryEntity> getHistories(long productId);

    @Nullable
    Optional<ProductNotificationHistoryEntity> findByProductIdAndRestockRound(long productId, long restockRound);
}
