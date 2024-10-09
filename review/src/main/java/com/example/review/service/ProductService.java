package com.example.review.service;

import com.example.review.domain.Product;
import com.example.review.exception.ProductNotFoundException;
import com.example.review.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Product 하나 가져오기
     *
     * @param productId
     * @return
     */
    public Product getSingleProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    /**
     * id, 리뷰 개수, 평점을 가져와서 update
     *
     * @param productId
     * @param totalCount
     * @param averageScore
     */
    @Transactional
    public void updateProductRating(Long productId, long totalCount, double averageScore) {
        productRepository.updateProductRate(productId, totalCount, roundToOneDecimal(averageScore));
    }

    private double roundToOneDecimal(double value) {
        return Math.floor(value * 10) / 10;
    }

}
