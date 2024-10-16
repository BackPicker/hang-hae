package com.hanghae.project.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(
        "UPDATE Product p " +
        "SET p.reviewCount = :reviewCount, p.totalScore = :totalScore " +
        "WHERE p.id = :productId"
    )
    void update(long productId, long reviewCount, long totalScore);
}
