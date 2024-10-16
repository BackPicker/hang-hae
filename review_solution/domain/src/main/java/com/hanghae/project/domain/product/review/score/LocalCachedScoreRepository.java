package com.hanghae.project.domain.product.review.score;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LocalCachedScoreRepository implements CachedScoreRepository {

    private final ConcurrentHashMap<Long, Long> scores = new ConcurrentHashMap<>();

    @Override
    public long add(long productId, int score) {
        long totalScore = scores.getOrDefault(productId, 0L) + score;
        scores.put(productId, totalScore);
        return totalScore;
    }

    @Override
    public void minus(long productId, int score) {
        long totalScore = scores.getOrDefault(productId, 0L)- score;
        scores.put(productId, totalScore);
    }

    @Override
    public long get(long productId) {
        return scores.getOrDefault(productId, 0L);
    }

    @Override
    public void save(long productId, long score) {
        scores.put(productId, score);
    }
}
