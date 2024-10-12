package com.example.restock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    @PostMapping(" /products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> SendRestockNotification(@PathVariable Long productId) {

        // 상품의 재고 수량을 확인한다

        // 재입고 회차를 1 증가시킨다

        // 재입고 알림을 설정한 유저들에게 알림 메세지를 전달해야 된다

        // 회차별 재입고 알림을 받은 유저 목록을 저장해야 한다.

        // 재입고 알림을 보내던 중 재고가 모두 없어진다면 알림 보내는 것을 중단합니다.

        // 재입고 알림 전송의 상태를 DB 에 저장해야 한다.

        return ResponseEntity.ok()
                .build();
    }


    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> manualResendRestockNotification(@PathVariable Long productId) {

        return ResponseEntity.ok()
                .build();
    }

}



