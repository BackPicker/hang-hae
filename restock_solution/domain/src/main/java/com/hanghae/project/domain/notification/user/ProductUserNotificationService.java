package com.hanghae.project.domain.notification.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUserNotificationService {

    private final ProductUserNotificationRepository repository;

    public ProductUserNotificationService(ProductUserNotificationRepository repository) {
        this.repository = repository;
    }

    public List<ProductUserNotification> getProductUserNotifications(long productId, @NotNull String cursor, int size) {
        return repository.getActiveProductUserNotifications(productId, cursor, size);
    }
}
