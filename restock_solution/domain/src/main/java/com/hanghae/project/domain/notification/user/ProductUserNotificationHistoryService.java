package com.hanghae.project.domain.notification.user;

import org.springframework.stereotype.Service;

@Service
public class ProductUserNotificationHistoryService {

    private final ProductUserNotificationHistoryRepository repository;

    public ProductUserNotificationHistoryService(ProductUserNotificationHistoryRepository repository) {
        this.repository = repository;
    }

    public void save(ProductUserNotificationHistory history) {
        repository.save(history);
    }
}
