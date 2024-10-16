package com.hanghae.project.infrastructure.product.score;

import com.hanghae.project.domain.product.review.score.ScoreRepository;
import com.hanghae.project.infrastructure.product.JpaProductRepository;
import com.hanghae.project.infrastructure.product.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class MysqlScoreRepository implements ScoreRepository {

    private final JpaProductRepository repository;

    public MysqlScoreRepository(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public long get(long productId) {
        return repository.findById(productId)
            .map(ProductEntity::getTotalScore)
            .orElseThrow(NoSuchElementException::new);
    }
}
