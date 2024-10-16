package com.hanghae.project.infrastructure.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "Product")
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    private long reviewCount;

    private long totalScore;

    protected ProductEntity() {
    }

    public ProductEntity(Long id, long reviewCount, long totalScore) {
        this.id = id;
        this.reviewCount = reviewCount;
        this.totalScore = totalScore;
    }

    public Long getId() {
        return id;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public long getTotalScore() {
        return totalScore;
    }
}
