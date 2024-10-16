package com.hanghae.project.application.review;

import com.hanghae.project.application.product.ProductReviewSynchronizer;
import com.hanghae.project.application.review.dto.GetReviewsResponse;
import com.hanghae.project.domain.common.lock.LockProvider;
import com.hanghae.project.domain.common.lock.LockUtil;
import com.hanghae.project.domain.common.transaction.TransactionDecorator;
import com.hanghae.project.domain.image.FileInfo;
import com.hanghae.project.domain.image.ImageUploader;
import com.hanghae.project.domain.product.review.count.ReviewCounter;
import com.hanghae.project.domain.product.review.score.ScoreCalculator;
import com.hanghae.project.domain.review.ReviewService;
import com.hanghae.project.domain.review.dto.Review;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class ReviewApplicationService {

    private static final Logger log = LoggerFactory.getLogger(ReviewApplicationService.class.getSimpleName());

    private static final String LOCK_KEY_PREFIX = "LOCK_KEY::";

    private final ReviewService reviewService;
    private final ReviewCounter reviewCounter;
    private final ScoreCalculator scoreCalculator;
    private final TransactionDecorator transactionDecorator;
    private final LockProvider lockProvider;
    private final ProductReviewSynchronizer productReviewSynchronizer;
    private final ImageUploader imageUploader;

    ReviewApplicationService(
        ReviewService reviewService,
        ReviewCounter reviewCounter,
        ScoreCalculator scoreCalculator,
        TransactionDecorator transactionDecorator,
        LockProvider lockProvider,
        ProductReviewSynchronizer productReviewSynchronizer,
        ImageUploader imageUploader
    ) {
        this.reviewService = reviewService;
        this.reviewCounter = reviewCounter;
        this.scoreCalculator = scoreCalculator;
        this.transactionDecorator = transactionDecorator;
        this.lockProvider = lockProvider;
        this.productReviewSynchronizer = productReviewSynchronizer;
        this.imageUploader = imageUploader;
    }

    public GetReviewsResponse getReviews(long productId, int size, @NotNull String cursor) {
        long reviewCount = reviewCounter.get(productId);
        long totalScore = scoreCalculator.get(productId);
        float score = getScore(totalScore, reviewCount, productId);

        List<Review> reviews = reviewService.getReviews(productId, size, cursor);

        final String newCursor;
        // reviews 가 size 만큼 존재하는 경우, cursor 는 마지막 원소의 reviewId 를 사용한다.
        if (reviews.size() == size) {
            newCursor = String.valueOf(reviews.get(size-1).id());
        } else newCursor = null;

        return new GetReviewsResponse(reviewCount, score, newCursor, reviews);
    }

    @Transactional
    public void add(
        long productId,
        long userId,
        int score,
        @NotNull String content,
        @Nullable FileInfo fileInfo
    ) throws IOException {

        // TODO: Exception 을 Duplicated 같은 걸로 수정
        if (reviewService.isExist(userId, productId)) {
            throw new IllegalArgumentException("Already existed review");
        }

        String imageUrl = null;
        if (fileInfo != null) {
            imageUrl = imageUploader.upload(fileInfo);
        }
        Review review = Review.create(productId, userId, score, content, imageUrl);
        reviewService.save(review);

        withLock(productId, () -> {
            reviewCounter.increase(productId);
            scoreCalculator.add(productId, score);
            productReviewSynchronizer.register(productId);
        });

        // 트랜잭션에 실패하는 경우, reviewCount 와 score 를 롤백한다.
        // synchronizer 는 다른 트랜잭션에서 sync 대상이 되었을 수 있기 때문에 rollback 을 하지 않는다.
        transactionDecorator.executeOnRollback(() -> {
            withLock(productId, () -> {
                reviewCounter.decrease(productId);
                scoreCalculator.minus(productId, score);
                productReviewSynchronizer.register(productId);
            });
        });
    }

    private float getScore(long totalScore, long reviewCount, long productId) {
        float score = totalScore / (reviewCount * 1.0f);
        if (score <= 0.0f) {
            log.error("Score is below than 0. productId: {}, score: {}", productId, score);
            return 0.1f;
        }

        if (score > 5.0f) {
            log.error("Score is higher than 5. productId: {}, score: {}", productId, score);
            return 5.0f;
        }

        return totalScore / (reviewCount * 1.0f);
    }

    private  void withLock(long productId, LockUtil.LockCallback function) {
        String key = LOCK_KEY_PREFIX + productId;
        LockUtil.withLock(key, lockProvider, function);
    }
}
