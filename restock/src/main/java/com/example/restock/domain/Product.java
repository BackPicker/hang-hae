package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 상품 테이블
 */
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품의 고유 식별자

    private String name; // 상품 이름

    private Integer stockQuantity; // 현재 재고 수량

    @Setter
    @Column(nullable = false)
    private Integer restockCount = 0; // 상품의 재입고 회차 (기본값 0)

    public Product(String name, Integer stockQuantity, Integer restockCount) {
        this.name          = name;
        this.stockQuantity = stockQuantity;
        this.restockCount  = restockCount;
    }
}