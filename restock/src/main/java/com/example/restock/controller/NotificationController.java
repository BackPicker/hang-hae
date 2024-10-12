package com.example.restock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @PostMapping(" /products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> SendRestockNotification(@PathVariable Long productId) {

        return ResponseEntity.ok()
                .build();
    }


    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> manualResendRestockNotification(@PathVariable Long productId) {

        return ResponseEntity.ok()
                .build();
    }

}



