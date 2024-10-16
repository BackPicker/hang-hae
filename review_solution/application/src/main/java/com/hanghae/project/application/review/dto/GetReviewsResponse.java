package com.hanghae.project.application.review.dto;

import com.hanghae.project.domain.review.dto.Review;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record GetReviewsResponse(
    long totalCount,
    float score,
    @Nullable String cursor,
    @NotNull List<Review> reviews
) { }
