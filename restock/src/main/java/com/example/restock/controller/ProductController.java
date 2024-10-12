package com.example.restock.controller;

import com.example.restock.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final NotificationService notificationService;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> SendRestockNotification(@PathVariable Long productId) {

        notificationService.sendRestockNotifications(productId);

        return ResponseEntity.ok()
                .build();
    }


    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> manualResendRestockNotification(@PathVariable Long productId) {

        return ResponseEntity.ok()
                .build();
    }

}



