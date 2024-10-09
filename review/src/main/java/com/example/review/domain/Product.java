package com.example.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(nullable = false)
    private int   reviewCount;
    @Column(nullable = false)
    private Float score;

    public Product(int reviewCount, Float score) {
        this.reviewCount = reviewCount;
        this.score       = score;
    }
}
