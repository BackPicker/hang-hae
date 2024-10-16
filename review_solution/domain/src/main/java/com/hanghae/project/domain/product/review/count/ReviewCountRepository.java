package com.hanghae.project.domain.product.review.count;

public interface ReviewCountRepository {
    long get(long productId);
}
