package com.hanghae.project.infrastructure.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query(
        """
        SELECT r FROM Review r
        WHERE 1=1
        AND r.productId = :productId
        AND r.id < :cursor
        ORDER BY r.id DESC
        LIMIT :size
        """
    )
    List<ReviewEntity> getReviews(long productId, int size, long cursor);

    boolean existsByUserIdAndProductId(long userId, long productId);
}
