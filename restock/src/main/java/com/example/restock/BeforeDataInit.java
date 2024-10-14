package com.example.restock;

import com.example.restock.domain.Product;
import com.example.restock.domain.ProductUserNotification;
import com.example.restock.repository.ProductRepository;
import com.example.restock.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@RequiredArgsConstructor
public class BeforeDataInit {

    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void beforeData() {
        Product itemA = new Product("itemA", 5000, 0);
        Product itemB = new Product("itemB", 5000, 0);
        productRepository.save(itemA);
        productRepository.save(itemB);

        for (int i = 1; i <= 1000; i++) {
            productUserNotificationRepository.save(new ProductUserNotification(itemA, (long) i));
            productUserNotificationRepository.save(new ProductUserNotification(itemB, (long) i));
        }

    }

}
