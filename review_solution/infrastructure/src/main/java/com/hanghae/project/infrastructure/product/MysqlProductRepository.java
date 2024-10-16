package com.hanghae.project.infrastructure.product;

import com.hanghae.project.domain.product.ProductRepository;
import com.hanghae.project.domain.product.dto.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MysqlProductRepository implements ProductRepository {

    private final JpaProductRepository repository;

    public MysqlProductRepository(JpaProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public void updateReviewProps(long productId, long reviewCount, long totalScore) {
        repository.update(productId, reviewCount, totalScore);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    private Product toDto(@NotNull ProductEntity entity) {
        return new Product(
            entity.getReviewCount(),
            entity.getTotalScore(),
            entity.getId()
        );
    }
}
