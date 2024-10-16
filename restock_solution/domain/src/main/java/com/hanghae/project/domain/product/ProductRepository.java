package com.hanghae.project.domain.product;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public interface ProductRepository {

    @Nullable
    Product findById(long id);

    void save(@NotNull Product product);
}
