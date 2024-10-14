package com.example.restock;

import com.example.restock.product.repository.ProductRepository;
import com.example.restock.notification.product_notification.repository.ProductUserNotificationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// @EnableScheduling
@SpringBootApplication
public class RestockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestockApplication.class, args);
    }

    @Bean
    public BeforeDataInit beforeDataInit(ProductRepository productRepository, ProductUserNotificationRepository productUserNotificationRepository) {
        return new BeforeDataInit(productRepository, productUserNotificationRepository);
    }

}
