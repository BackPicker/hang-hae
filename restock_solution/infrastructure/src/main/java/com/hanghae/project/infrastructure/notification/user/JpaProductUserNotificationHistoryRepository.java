package com.hanghae.project.infrastructure.notification.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistoryEntity, Long> {
}
