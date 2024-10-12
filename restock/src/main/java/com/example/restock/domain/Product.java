package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품의 고유 식별자

    private String name; // 상품 이름

    private Integer stock; // 현재 재고 수량

    @Column(name = "restock_count", nullable = false)
    private Integer restockCount = 0; // 상품의 재입고 회차 (기본값 0)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정 시간

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 현재 시간으로 설정
        updatedAt = LocalDateTime.now(); // 현재 시간으로 설정
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // 현재 시간으로 설정
    }

    public Product(String name, Integer stock, Integer restockCount) {
        this.name         = name;
        this.stock        = stock;
        this.restockCount = restockCount;
    }
}