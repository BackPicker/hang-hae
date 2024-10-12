package com.example.restock.service;

import com.example.restock.domain.NotificationStatus;
import com.example.restock.domain.Product;
import com.example.restock.domain.ProductNotificationHistory;
import com.example.restock.domain.ProductUserNotification;
import com.example.restock.repository.ProductNotificationHistoryRepository;
import com.example.restock.repository.ProductRepository;
import com.example.restock.repository.ProductUserNotificationHistoryRepository;
import com.example.restock.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ProductRepository                        productRepository;
    private final ProductNotificationHistoryRepository     productNotificationHistoryRepository;
    private final ProductUserNotificationRepository        productUserNotificationRepository;
    private final ProductUserNotificationHistoryRepository userNotificationHistoryRepository;

    public void sendRestockNotifications(Long productId) {
        // 상품의 재고 수량을 확인한다
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 재입고 회차를 1 증가시킨다
        product.setRestockCount(product.getRestockCount() + 1);
        productRepository.save(product);
        log.info("product = {}", product);

        // 재입고 알림을 설정한 유저들에게 알림 메세지를 전달해야 된다
        ProductNotificationHistory history = new ProductNotificationHistory(product, NotificationStatus.IN_PROGRESS, LocalDateTime.now());
        productNotificationHistoryRepository.save(history);

        // 회차별 재입고 알림을 받은 유저 목록을 저장해야 한다.
        List<ProductUserNotification> userNotifications = productUserNotificationRepository.findByProductId(productId);
        for (ProductUserNotification userNotification : userNotifications) {
            log.info("userNotification = {}", userNotification);
        }

        // 재입고 알림을 보내던 중 재고가 모두 없어진다면 알림 보내는 것을 중단합니다.

        // 재입고 알림 전송의 상태를 DB 에 저장해야 한다.

    }



}
