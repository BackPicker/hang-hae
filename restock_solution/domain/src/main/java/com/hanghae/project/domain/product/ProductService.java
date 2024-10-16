package com.hanghae.project.domain.product;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Nullable
    public Product findById(long id) {
        return repository.findById(id);
    }

    public void save(Product product) {
        repository.save(product);
    }
}
