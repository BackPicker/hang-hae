package com.example.restock.controller;


import com.example.restock.service.ProductRestockNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestockNotificationController {

    private final ProductRestockNotificationService notificationService;

    // 재입고 알림 전송 API
    @PostMapping("/{productId}/notifications/re-stock")
    public ResponseEntity<String> sendRestockNotification(@PathVariable Long productId) {
        notificationService.sendRestockNotification(productId);
        return ResponseEntity.ok("Notification process started.");
    }

    // 재입고 알림 전송 API (manual)
    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<String> manualSendRestockNotification(@PathVariable Long productId) {
        notificationService.manualSendRestockNotification(productId);
        return ResponseEntity.ok("Manual notification process started.");
    }
}