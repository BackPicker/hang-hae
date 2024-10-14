package com.example.restock.scheduler;

import com.example.restock.domain.Product;
import com.example.restock.repository.ProductRepository;
import com.example.restock.service.ProductRestockNotificationService;
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
    private final ProductRepository                 productRepository;

    @Scheduled(cron = "*/1 * * * * *")
    public void updateQuantity() {
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            int stock = product.getStock();
            if (stock > 0) {
                try {
                    // 초기 인덱스는 0으로 설정하여 처음부터 알림을 보냄
                    productRestockNotificationService.sendRestockNotification(product.getId(), 0);
                } catch (Exception e) {
                    log.error("Error sending restock notification for product ID {}: {}", product.getId(), e.getMessage());
                    // 예외가 발생했을 경우 로그를 남기고 계속 진행
                }
            }
        }
    }
}
