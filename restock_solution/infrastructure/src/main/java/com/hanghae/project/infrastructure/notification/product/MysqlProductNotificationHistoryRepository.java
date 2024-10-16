package com.hanghae.project.infrastructure.notification.product;

import com.hanghae.project.domain.notification.product.ProductNotificationHistory;
import com.hanghae.project.domain.notification.product.ProductNotificationHistoryRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Comparator;

@Repository
public class MysqlProductNotificationHistoryRepository implements ProductNotificationHistoryRepository {

    private final JpaProductNotificationHistoryRepository repository;

    public MysqlProductNotificationHistoryRepository(JpaProductNotificationHistoryRepository repository) {
        this.repository = repository;
    }

    @Nullable
    @Override
    public ProductNotificationHistory findLatestHistory(long productId) {
        return repository.getHistories(productId)
            .stream()
            .max(Comparator.comparingInt(history -> Math.toIntExact(history.getRestockRound())))
            .map(this::toDto)
            .orElse(null);
    }

    @Nullable
    @Override
    public ProductNotificationHistory findByProductIdAndRestockRound(long productId, long restockRound) {
        return repository.findByProductIdAndRestockRound(productId, restockRound).map(this::toDto).orElse(null);
    }

    @Override
    public void save(ProductNotificationHistory history) {
        ProductNotificationHistoryEntity entity = toEntity(history);
        repository.save(entity);
    }

    private ProductNotificationHistory toDto(@NotNull ProductNotificationHistoryEntity entity) {
        return new ProductNotificationHistory(
            entity.getId(),
            entity.getProductId(),
            entity.getRestockRound(),
            entity.getLastSentUserId(),
            entity.getState()
        );
    }

    private ProductNotificationHistoryEntity toEntity(@NotNull ProductNotificationHistory history) {
        if (history.id() == 0L) {
            return new ProductNotificationHistoryEntity(
                history.productId(),
                history.restockRound(),
                history.lastSentUserId(),
                history.state()
            );
        }

        return new ProductNotificationHistoryEntity(
            history.id(),
            history.productId(),
            history.restockRound(),
            history.lastSentUserId(),
            history.state()
        );
    }
}
