package com.hanghae.project.infrastructure.product.review;

import com.hanghae.project.domain.product.review.count.ReviewCountRepository;
import com.hanghae.project.infrastructure.product.JpaProductRepository;
import com.hanghae.project.infrastructure.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class MysqlReviewCountRepository implements ReviewCountRepository {

    private final JpaProductRepository repository;

    public MysqlReviewCountRepository(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public long get(long productId) {
        return repository.findById(productId)
            .map(ProductEntity::getReviewCount)
            .orElseThrow(NoSuchElementException::new);
    }
}