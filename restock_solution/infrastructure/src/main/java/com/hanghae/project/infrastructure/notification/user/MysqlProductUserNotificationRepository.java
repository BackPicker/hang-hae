package com.hanghae.project.infrastructure.notification.user;

import com.hanghae.project.domain.notification.user.ProductUserNotification;
import com.hanghae.project.domain.notification.user.ProductUserNotificationRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlProductUserNotificationRepository implements ProductUserNotificationRepository {

    private final JpaProductUserNotificationRepository repository;

    public MysqlProductUserNotificationRepository(JpaProductUserNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductUserNotification> getActiveProductUserNotifications(long productId, String cursor, int size) {
        return repository.getActiveProductUserNotifications(productId, Long.parseLong(cursor), size)
            .stream()
            .map(this::toDto)
            .toList();
    }

    private ProductUserNotification toDto(@NotNull ProductUserNotificationEntity entity) {
        return new ProductUserNotification(
            entity.getId(),
            entity.getProductId(),
            entity.getUserId(),
            entity.getCreatedAt()
        );
    }
}
