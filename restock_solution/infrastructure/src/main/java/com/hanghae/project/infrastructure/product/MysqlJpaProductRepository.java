package com.hanghae.project.infrastructure.product;

import com.hanghae.project.domain.product.Product;
import com.hanghae.project.domain.product.ProductRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class MysqlJpaProductRepository implements ProductRepository {

    private final JpaProductRepository repository;

    public MysqlJpaProductRepository(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Nullable
    @Override
    public Product findById(long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public void save(Product product) {
        ProductEntity entity = toEntity(product);
        repository.save(entity);
    }

    private Product toDto(@NotNull ProductEntity entity) {
        return new Product(
            entity.getId(),
            entity.getRestockRound(),
            entity.getQuantity()
        );
    }

    private ProductEntity toEntity(@NotNull Product product) {
        return new ProductEntity(
            product.id(),
            product.restockRound(),
            product.quantity()
        );
    }
}
