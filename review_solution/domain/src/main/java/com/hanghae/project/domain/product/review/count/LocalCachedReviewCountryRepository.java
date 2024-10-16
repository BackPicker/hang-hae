package com.hanghae.project.domain.product.review.count;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Profile("!prod")
@Repository
public class LocalCachedReviewCountryRepository implements CachedReviewCountRepository {

    private final ConcurrentHashMap<Long, Long> reviewCounts = new ConcurrentHashMap<>();

    @Override
    public void increase(long productId) {
        long count = reviewCounts.getOrDefault(productId, 0L) + 1;
        reviewCounts.put(productId, count);
    }

    @Override
    public void decrease(long productId) {
        long count = reviewCounts.getOrDefault(productId, 0L) - 1;
        reviewCounts.put(productId, count);
    }

    @Override
    public long get(long productId) {
        return reviewCounts.getOrDefault(productId, 0L);
    }

    @Override
    public void save(long productId, long reviewCount) {
        reviewCounts.put(productId, reviewCount);
    }
}
