package com.hanghae.project.domain.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void update(long productId, long reviewCount, long totalScore) {
        repository.updateReviewProps(productId, reviewCount, totalScore);
    }
}
