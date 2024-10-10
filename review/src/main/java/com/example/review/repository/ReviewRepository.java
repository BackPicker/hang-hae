package com.example.review.repository;

import com.example.review.domain.Review;
import com.example.review.domain.dto.ReviewStatsDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.user u JOIN FETCH r.product p " +
            "WHERE p.id = :productId AND (:cursor IS NULL OR r.id > :cursor) " +
            "ORDER BY r.id ASC")
    List<Review> findByProductId(@Param("productId") Long productId, @Param("cursor") Long cursor, PageRequest pageable);

    @Query("SELECT new com.example.review.domain.dto.ReviewStatsDto(COUNT(r), AVG(r.score)) FROM Review r WHERE r.product.id = :productId")
    ReviewStatsDto findReviewCountAndAverageScoreByProductId(Long productId);
}