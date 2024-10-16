package com.hanghae.project.domain.product;

import com.hanghae.project.domain.product.dto.Product;

import java.util.Optional;

public interface ProductRepository {

    void updateReviewProps(long productId, long reviewCount, long totalScore);

    Optional<Product> findById(Long id);
}
