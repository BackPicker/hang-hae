package com.hanghae.project.domain.review.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Review(
    long id,
    long productId,
    long userId,
    int score,
    @NotNull String content,
    @Nullable String imageUrl,
    @NotNull LocalDateTime createAt
) {
    public static Review create(
        long productId,
        long userId,
        int score,
        @NotNull String content,
        @Nullable String imageUrl
    ) {
        return new Review(
            0L,
            productId,
            userId,
            score,
            content,
            imageUrl,
            LocalDateTime.now()
        );
    }
}
