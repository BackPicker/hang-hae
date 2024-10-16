package com.hanghae.project.infrastructure.notification.user;

import com.hanghae.project.domain.notification.user.ProductUserNotificationHistory;
import com.hanghae.project.domain.notification.user.ProductUserNotificationHistoryRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class MysqlProductUserNotificationHistoryRepository implements ProductUserNotificationHistoryRepository {

    private final JpaProductUserNotificationHistoryRepository repository;

    public MysqlProductUserNotificationHistoryRepository(JpaProductUserNotificationHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(ProductUserNotificationHistory history) {
        ProductUserNotificationHistoryEntity entity = toEntity(history);
        repository.save(entity);
    }

    private ProductUserNotificationHistoryEntity toEntity(@NotNull ProductUserNotificationHistory history) {
        return new ProductUserNotificationHistoryEntity(
            history.productId(),
            history.userId(),
            history.restockRound(),
            history.sentAt()
        );
    }
}
