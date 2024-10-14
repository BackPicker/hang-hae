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
                productRestockNotificationService.sendRestockNotification(product.getId());
            }

        }

    }

}
