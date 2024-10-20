package com.example.restock.scheduler;

import com.example.restock.product.domain.Product;
import com.example.restock.product.repository.ProductRepository;
import com.example.restock.product.service.ProductRestockNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ProductRestockNotificationService productRestockNotificationService;
    private final ProductRepository productRepository;

    @Scheduled(cron = "*/5 * * * * *")
    public void updateQuantity() {
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            int stock = product.getStock();
            if (stock > 0) {
                try {
                    // 초기 인덱스는 0으로 설정하여 처음부터 알림을 보냄
                    productRestockNotificationService.sendRestockNotification(product.getId(), 0);
                } catch (Exception e) {
                    log.error("상품 ID {} 에 대한 재입고 알림 전송 중 오류 발생: {}", product.getId(), e.getMessage());
                }
            }
        }
    }
}
