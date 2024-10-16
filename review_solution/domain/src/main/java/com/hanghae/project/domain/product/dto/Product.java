package com.hanghae.project.domain.product.dto;

public record Product(
    long reviewCount,
    long totalScore,
    long id
) {}