package com.hanghae.project.domain.notification;

import com.hanghae.project.domain.notification.user.ProductUserNotificationHistory;
import com.hanghae.project.domain.notification.user.ProductUserNotificationHistoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Profile("!prod")
@Service
public class TestSendNotificationService implements SendNotificationService {

    private final ProductUserNotificationHistoryService productUserNotificationHistoryService;

    public TestSendNotificationService(ProductUserNotificationHistoryService productUserNotificationHistoryService) {
        this.productUserNotificationHistoryService = productUserNotificationHistoryService;
    }

    // TODO: 별도 executor 에서 실행하게끔 수정
    @Async
    @Override
    public void send(long productId, long userId, long restockRound) {
        ProductUserNotificationHistory history = ProductUserNotificationHistory.sent(productId, userId, restockRound);
        productUserNotificationHistoryService.save(history);
    }
}
