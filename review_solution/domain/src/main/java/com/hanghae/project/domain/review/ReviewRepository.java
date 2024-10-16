package com.hanghae.project.domain.review;

import com.hanghae.project.domain.review.dto.Review;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ReviewRepository {

    List<Review> getReviews(long productId, int size, @NotNull String cursor);

    void save(@NotNull Review review);

    boolean isExist(long userId, long productId);
}
