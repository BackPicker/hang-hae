package com.hanghae.project.domain.product.review.score;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScoreCalculator {

    private static final Logger log = LoggerFactory.getLogger(ScoreCalculator.class.getSimpleName());

    private final CachedScoreRepository cachedScoreRepository;

    private final ScoreRepository scoreRepository;

    public ScoreCalculator(
        CachedScoreRepository cachedScoreRepository,
        ScoreRepository scoreRepository
    ) {
        this.cachedScoreRepository = cachedScoreRepository;
        this.scoreRepository = scoreRepository;
    }

    public long add(long productId, int addedScore) {
        long cachedScore = get(productId);
        if (cachedScore == 0L) {
            return cachedScoreRepository.add(productId, addedScore);
        }

        long score = scoreRepository.get(productId);
        cachedScoreRepository.save(productId, score);
        return cachedScoreRepository.add(productId, addedScore);
    }

    // 리뷰 트랜잭션에 실패했을 때, 롤백을 위한 로직
    public void minus(long productId, int removedScore) {
        long cachedScore = get(productId);
        if (cachedScore == 0L) {
            cachedScoreRepository.minus(productId, removedScore);
            return;
        }

        long score = scoreRepository.get(productId);
        if (score == 0L) {
            log.error("minus method is called even though score is zero. productId: {}", productId);
            return;
        }

        cachedScoreRepository.save(productId, score);
        cachedScoreRepository.minus(productId, removedScore);
    }

    public long get(long productId) {
        long cachedScore = cachedScoreRepository.get(productId);
        if (cachedScore > 0L) {
            return cachedScore;
        }

        long score = scoreRepository.get(productId);
        cachedScoreRepository.save(productId, score);
        return score;
    }
}
