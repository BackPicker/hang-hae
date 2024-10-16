package com.hanghae.project.application.product;

import com.hanghae.project.domain.product.ProductService;
import com.hanghae.project.domain.product.review.count.ReviewCounter;
import com.hanghae.project.domain.product.review.score.ScoreCalculator;
import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductReviewSynchronizer {

    private final ConcurrentHashMap<String, HashSet<Long>> targets = new ConcurrentHashMap<>();

    private final ReviewCounter reviewCounter;
    private final ScoreCalculator scoreCalculator;
    private final ProductService productService;

    public ProductReviewSynchronizer(
        ReviewCounter reviewCounter,
        ScoreCalculator scoreCalculator,
        ProductService productService
    ) {
        this.reviewCounter = reviewCounter;
        this.scoreCalculator = scoreCalculator;
        this.productService = productService;
    }

    // 서버가 종료될 때, 로컬에 저장되어 있는 값을 DB 에 sync 한다.
    @PreDestroy
    public void shutdown() {
        String previousTimeWindow = getPreviousTimeWindow();
        String currentTimeWindow = getCurrentTimeWindow();
        synchronize(previousTimeWindow);
        synchronize(currentTimeWindow);
    }

    // 매분 5초에 synchronize 합니다.
    @Scheduled(cron = "5 * * * * *")
    public void synchronize() {
        String previousTimeWindow = getPreviousTimeWindow();
        synchronize(previousTimeWindow);
    }

    private void synchronize(@NotNull String timeWindow) {
        HashSet<Long> targetProductIds = targets.get(timeWindow);

        // 이전 윈도우 내에서 synchronize 대상이 존재하지 않는다면, early-return
        if (targetProductIds == null || targetProductIds.isEmpty()) {
            return;
        }

        targetProductIds
            .parallelStream()
            .forEach(productId -> {
                long count = reviewCounter.get(productId);
                long totalScore = scoreCalculator.get(productId);
                productService.update(productId, count, totalScore);
            });
    }

    public void register(long productId) {
        String currentTimeWindow = getCurrentTimeWindow();
        targets.computeIfAbsent(currentTimeWindow, k -> new HashSet<>()).add(productId);
    }

    @NotNull
    private String getCurrentTimeWindow() {
        LocalDateTime now = LocalDateTime.now();
        return getTimeWindow(now);
    }

    @NotNull
    private String getPreviousTimeWindow() {
        LocalDateTime oneMinuteBefore = LocalDateTime.now().minusMinutes(1);
        return getTimeWindow(oneMinuteBefore);
    }

    @NotNull
    private String getTimeWindow(@NotNull LocalDateTime localDateTime) {
        return localDateTime.truncatedTo(ChronoUnit.MINUTES).toString();
    }
}
