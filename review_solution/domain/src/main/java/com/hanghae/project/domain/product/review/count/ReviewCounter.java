package com.hanghae.project.domain.product.review.count;

import org.springframework.stereotype.Service;

@Service
public class ReviewCounter {

    private final CachedReviewCountRepository cachedReviewCountRepository;

    private final ReviewCountRepository reviewCountRepository;

    public ReviewCounter(
        CachedReviewCountRepository cachedReviewCountRepository,
        ReviewCountRepository reviewCountRepository
    ) {
        this.cachedReviewCountRepository = cachedReviewCountRepository;
        this.reviewCountRepository = reviewCountRepository;
    }

    public void increase(long productId) {
        long cachedReviewCount = cachedReviewCountRepository.get(productId);
        if (cachedReviewCount > 0) {
            cachedReviewCountRepository.increase(productId);
            return;
        }

        long reviewCount = reviewCountRepository.get(productId);
        cachedReviewCountRepository.save(productId, reviewCount);
        cachedReviewCountRepository.increase(productId);
    }

    public void decrease(long productId) {
        long cachedReviewCount = cachedReviewCountRepository.get(productId);
        if (cachedReviewCount > 0) {
            cachedReviewCountRepository.decrease(productId);
            return;
        }

        long reviewCount = reviewCountRepository.get(productId);
        cachedReviewCountRepository.save(productId, reviewCount);
        cachedReviewCountRepository.decrease(productId);
    }

    public long get(long productId) {
        long cachedReviewCount = cachedReviewCountRepository.get(productId);
        if (cachedReviewCount > 0) {
            return cachedReviewCount;
        }

        long reviewCount = reviewCountRepository.get(productId);
        cachedReviewCountRepository.save(productId, reviewCount);
        return reviewCount;
    }
}
