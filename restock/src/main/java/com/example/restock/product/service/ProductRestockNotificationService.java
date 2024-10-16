package com.example.restock.product.service;

import com.example.restock.exception.NotificationSendingException;
import com.example.restock.exception.ProductNotFoundException;
import com.example.restock.notification.product_notification.domain.NotificationStatus;
import com.example.restock.notification.product_notification.domain.ProductNotificationHistory;
import com.example.restock.notification.product_notification.repository.ProductUserNotificationRepository;
import com.example.restock.notification.product_user_notification.domain.ProductUserNotification;
import com.example.restock.notification.product_user_notification.domain.ProductUserNotificationHistory;
import com.example.restock.notification.product_user_notification.domain.ProductUserNotificationStatus;
import com.example.restock.notification.product_user_notification.repository.ProductNotificationHistoryRepository;
import com.example.restock.notification.product_user_notification.repository.ProductUserNotificationHistoryRepository;
import com.example.restock.product.aop.Retryable;
import com.example.restock.product.domain.Product;
import com.example.restock.product.repository.ProductRepository;
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

    private static final int MAX_REQUESTS_PER_SECOND = 500;

    @Transactional
    public void sendRestockNotification(Long productId, int startIndex) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다"));

        // 재입고 회차 증가 및 저장
        product.setRestockCount(product.getRestockCount() + 1);
        productRepository.save(product);

        // 알림 상태 기록
        ProductNotificationHistory notificationHistory = new ProductNotificationHistory(product, NotificationStatus.IN_PROGRESS);
        notificationHistoryRepository.save(notificationHistory);

        // 알림 전송 처리
        processNotifications(productId, startIndex, notificationHistory, product);
    }

    @Transactional
    public void manualSendRestockNotification(Long productId, int startIndex) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품을 찾을 수 없습니다"));

        // 재입고 회차 증가 및 저장
        product.setRestockCount(product.getRestockCount() + 1);
        productRepository.save(product);

        // 알림 상태 기록
        ProductNotificationHistory notificationHistory = new ProductNotificationHistory(product, NotificationStatus.IN_PROGRESS);
        notificationHistoryRepository.save(notificationHistory);

        // 알림 전송 처리
        processNotifications(productId, startIndex, notificationHistory, product);
    }

    private void processNotifications(Long productId, int startIndex, ProductNotificationHistory notificationHistory, Product product) {
        List<ProductUserNotification> userNotifications = userNotificationRepository.findAllByProductIdAndUserNotificationStatus(productId, ProductUserNotificationStatus.WAIT);

        if (startIndex < 0 || startIndex >= userNotifications.size()) {
            throw new IllegalArgumentException("유저 알림에 대한 시작 인덱스가 잘못되었습니다");
        }

        long startTime    = System.currentTimeMillis();
        int  requestCount = 0;

        for (int i = startIndex; i < userNotifications.size(); i++) {
            ProductUserNotification userNotification = userNotifications.get(i);

            // 재고 부족 시 예외 처리
            if (product.getStock() <= 0) {
                notificationHistory.setStatus(NotificationStatus.CANCELED_BY_SOLD_OUT);
                notificationHistoryRepository.save(notificationHistory);
                return; // 알림 중단
            }

            // 알림 전송
            try {
                sendNotification(productId, userNotification.getUserId());
            } catch (NotificationSendingException e) {
                log.error("알림 전송 중 오류 발생: {}", e.getMessage());
                notificationHistory.setStatus(NotificationStatus.CANCELED_BY_ERROR);
                notificationHistoryRepository.save(notificationHistory);
                return; // 알림 중단
            }

            // 성공적인 전송 기록
            ProductUserNotificationHistory userHistory = new ProductUserNotificationHistory(product, userNotification.getUserId());
            userNotificationHistoryRepository.save(userHistory);
            userNotification.setUserNotificationStatus(ProductUserNotificationStatus.SEND);

            // 재고 감소
            product.setStock(product.getStock() - 1);

            requestCount++;

            // 초당 요청 수 제한
            if (requestCount >= MAX_REQUESTS_PER_SECOND) {
                manageRateLimiting(startTime);
                requestCount = 0;
                startTime    = System.currentTimeMillis();
            }
        }

        notificationHistory.setStatus(NotificationStatus.COMPLETED);
        notificationHistoryRepository.save(notificationHistory);
    }

    @Retryable(value = 3)
    private void sendNotification(Long productId, Long userId) {
        String message = "상품이 재입고되었습니다.";
        log.info("상품 ID = {}, 사용자 ID {}: 알림이 성공적으로 전송되었습니다: {}", productId, userId, message);
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
