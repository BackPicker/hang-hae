package com.hanghae.project.domain.product.review.score;

public interface CachedScoreRepository {

    long add(long productId, int score);

    void minus(long productId, int score);

    long get(long productId);

    void save(long productId, long score);
}
