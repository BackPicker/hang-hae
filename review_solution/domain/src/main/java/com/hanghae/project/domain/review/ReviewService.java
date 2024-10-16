package com.hanghae.project.domain.review;

import com.hanghae.project.domain.review.dto.Review;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public List<Review> getReviews(long productId, int size, @NotNull String cursor) {
        return repository.getReviews(productId, size, cursor);
    }

    @Transactional
    public void save(@NotNull Review review) {
        repository.save(review);
    }

    public boolean isExist(long userId, long productId) {
        return repository.isExist(userId, productId);
    }
}
