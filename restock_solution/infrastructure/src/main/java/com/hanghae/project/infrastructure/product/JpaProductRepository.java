package com.hanghae.project.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {}
