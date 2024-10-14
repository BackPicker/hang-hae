package com.example.restock.controller;


import com.example.restock.service.ProductRestockNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestockNotificationController {

    private final ProductRestockNotificationService notificationService;

    // 재입고 알림 전송 API
    @PostMapping("/{productId}/notifications/re-stock")
    public ResponseEntity<String> sendRestockNotification(@PathVariable Long productId) {
        notificationService.sendRestockNotification(productId, 0); // 기본적으로 0부터 시작
        return ResponseEntity.ok("Notification process started.");
    }

    // 재입고 알림 전송 API (manual)
    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<String> manualSendRestockNotification(@PathVariable Long productId,
                                                                @RequestParam(required = false) Integer lastSuccessIndex) {
        notificationService.manualSendRestockNotification(productId, lastSuccessIndex);
        return ResponseEntity.ok("Manual notification process started.");
    }
}