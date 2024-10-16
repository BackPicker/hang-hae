package com.hanghae.project.infrastructure.notification.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaProductUserNotificationRepository extends JpaRepository<ProductUserNotificationEntity, Long> {

    @Query("""
        SELECT p FROM ProductUserNotification p
        WHERE 1=1
        AND p.productId = :productId
        AND p.id > :cursor
        ORDER BY p.id ASC
        LIMIT :size
    """
    )
    List<ProductUserNotificationEntity> getActiveProductUserNotifications(long productId, long cursor, int size);
}
