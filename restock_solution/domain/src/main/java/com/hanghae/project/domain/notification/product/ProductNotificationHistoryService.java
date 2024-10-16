package com.hanghae.project.domain.notification.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductNotificationHistoryService {

    private static final Logger log = LoggerFactory.getLogger(ProductNotificationHistoryService.class.getSimpleName());

    private final ProductNotificationHistoryRepository repository;

    public ProductNotificationHistoryService(ProductNotificationHistoryRepository repository) {
        this.repository = repository;
    }

    @Nullable
    public ProductNotificationHistory findLatestHistory(long productId) {
        return repository.findLatestHistory(productId);
    }

    public void save(@NotNull ProductNotificationHistory history) {
        repository.save(history);
    }
}
