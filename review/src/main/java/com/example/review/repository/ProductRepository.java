package com.example.review.repository;

import com.example.review.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product p SET p.reviewCount = :reviewCount, p.score = :score WHERE p.id = :productId")
    void updateProductRate(@Param("productId") Long productId, @Param("reviewCount") long reviewCount, @Param("score") double score);

    Optional<Product> findById(Long productId);
}

