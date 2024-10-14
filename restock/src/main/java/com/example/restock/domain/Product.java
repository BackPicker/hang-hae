package com.example.restock.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private int stock;

    @Setter
    @Column(nullable = false)
    private int restockCount = 0;  // 재입고 회차

    public Product(String name, int stock, int restockCount) {
        this.name         = name;
        this.stock        = stock;
        this.restockCount = restockCount;
    }
}
