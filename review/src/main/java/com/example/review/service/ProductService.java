package com.example.review.service;

import com.example.review.domain.Product;
import com.example.review.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository         productRepository;
    private final RedissonClient            redissonClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Product 하나 가져오기
     */
    @Transactional
    public Product getSingleProduct(Long productId) {
        RLock lock = redissonClient.getLock("TEST");
        try {
            if (lock.tryLock(5, 3, TimeUnit.SECONDS)) {
                return productRepository.findById(productId)
                        .get();
            } else {
                throw new RuntimeException();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                applicationEventPublisher.publishEvent(lock);
            }
        }
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void txLogic(RLock rLock) {
        rLock.unlock();
    }

    /**
     * id, 리뷰 개수, 평점을 가져와서 update
     */
    @Transactional
    public void updateProductRating(Long productId, long totalCount, double averageScore) {
        productRepository.updateProductRate(productId, totalCount, roundToOneDecimal(averageScore));
    }

    private double roundToOneDecimal(double value) {
        return Math.floor(value * 10) / 10;
    }

}
