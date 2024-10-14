package com.example.restock.product.controller;

import com.example.restock.product.service.ProductRestockNotificationService;
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
        notificationService.sendRestockNotification(productId, 0);
        return ResponseEntity.ok("알림 전송 프로세스가 시작되었습니다.");
    }

    // 재입고 알림 전송 API (수동)
    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<String> manualSendRestockNotification(@PathVariable Long productId, @RequestParam(defaultValue = "0") int startIndex) {
        notificationService.manualSendRestockNotification(productId, startIndex);
        return ResponseEntity.ok("수동 알림 전송 프로세스가 시작되었습니다.");
    }
}
