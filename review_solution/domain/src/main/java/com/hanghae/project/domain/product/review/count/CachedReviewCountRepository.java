package com.hanghae.project.domain.product.review.count;

public interface CachedReviewCountRepository {

    void increase(long productId);

    void decrease(long productId);

    long get(long productId);

    void save(long productId, long reviewCount);
}
