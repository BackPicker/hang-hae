package com.hanghae.project.infrastructure.review;

import com.hanghae.project.domain.review.ReviewRepository;
import com.hanghae.project.domain.review.dto.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MysqlReviewRepository implements ReviewRepository {

    private final JpaReviewRepository repository;

    public MysqlReviewRepository(JpaReviewRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Review> getReviews(long productId, int size, String cursor) {
        return repository.getReviews(productId, size, Long.parseLong(cursor))
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    public void save(Review review) {
        ReviewEntity entity = toEntity(review);
        repository.save(entity);
    }

    @Override
    public boolean isExist(long userId, long productId) {
        return repository.existsByUserIdAndProductId(userId, productId);
    }

    private ReviewEntity toEntity(Review review) {
        return new ReviewEntity(
            review.productId(),
            review.userId(),
            review.score(),
            review.content(),
            review.imageUrl()
        );
    }

    private Review toDto(ReviewEntity entity) {
        return new Review(
            entity.getId(),
            entity.getProductId(),
            entity.getUserId(),
            entity.getScore(),
            entity.getContent(),
            entity.getImageUrl(),
            entity.getCreatedAt()
        );
    }
}
