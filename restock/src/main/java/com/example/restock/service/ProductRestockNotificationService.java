package com.example.restock.service;

import com.example.restock.domain.*;
import com.example.restock.exception.ProductNotFoundException;
import com.example.restock.repository.ProductNotificationHistoryRepository;
import com.example.restock.repository.ProductRepository;
import com.example.restock.repository.ProductUserNotificationHistoryRepository;
import com.example.restock.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRestockNotificationService {
    private final ProductRepository                        productRepository;
    private final ProductUserNotificationRepository        userNotificationRepository;
    private final ProductNotificationHistoryRepository     notificationHistoryRepository;
    private final ProductUserNotificationHistoryRepository userNotificationHistoryRepository;

    @Transactional
    public void sendRestockNotification(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // 재입고 알림을 전송하기 전, 상품의 재입고 회차를 1 증가 시킨다.
        product.setRestockCount(product.getRestockCount() + 1);
        productRepository.save(product);

        // 알림 상태 기록
        ProductNotificationHistory notificationHistory = new ProductNotificationHistory(product, NotificationStatus.IN_PROGRESS);
        notificationHistoryRepository.save(notificationHistory);

        // 상품이 재입고 되었을 때, 재입고 알림을 설정한 유저들에게 알림 메시지를 전달해야 한다.
        // 설정된 유저 리스트 조회
        List<ProductUserNotification> userNotifications = userNotificationRepository.findAllByProductIdAndUserNotificationStatus(productId, ProductUserNotificationStatus.WAIT);

        long startTime    = System.currentTimeMillis();
        int  requestCount = 0;

        for (ProductUserNotification userNotification : userNotifications) {
            // 재입고 알림을 보내던 중 재고가 모두 없어진다면 알림 보내는 것을 중단합니다.
            if (product.getStock() <= 0) {
                notificationHistory.setStatus(NotificationStatus.CANCELED_BY_SOLD_OUT);
                notificationHistoryRepository.save(notificationHistory);
                return;
            }

            // 알림 전송 로직?
            sendNotification(productId, userNotification.getUserId());
            // 성공적으로 전송된 경우 히스토리 기록
            ProductUserNotificationHistory userHistory = new ProductUserNotificationHistory(product, userNotification.getUserId());
            userNotificationHistoryRepository.save(userHistory);
            // 성공적으로 전송된 경우 WAIT -> SEND 변경
            userNotification.setUserNotificationStatus(ProductUserNotificationStatus.SEND);

            // 재고 감소
            product.setStock(product.getStock() - 1);
            productRepository.save(product);

            requestCount++;

            int MAX_REQUESTS_PER_SECOND = 500;
            if (requestCount >= MAX_REQUESTS_PER_SECOND) {
                manageRateLimiting(startTime);
                requestCount = 0;
                startTime    = System.currentTimeMillis();
            }

        }

        notificationHistory.setStatus(NotificationStatus.COMPLETED);
        notificationHistoryRepository.save(notificationHistory);
    }

    @Transactional
    public void manualSendRestockNotification(Long productId) {
        sendRestockNotification(productId);
    }

    private void sendNotification(Long productId, Long userId) {
        String message = "상품이 재입고되었습니다.";
        log.info("Product Id = {}, User ID {}: Notification sent successfully: {}", productId, userId, message);
    }


    private void manageRateLimiting(long startTime) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long waitTime    = 1000 - elapsedTime;

        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread()
                        .interrupt();
            }
        }
    }
}