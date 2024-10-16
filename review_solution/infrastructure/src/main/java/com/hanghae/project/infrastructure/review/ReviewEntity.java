package com.hanghae.project.infrastructure.review;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity(name = "Review")
public class ReviewEntity {

    @Id
    @GeneratedValue
    private long id;

    private long productId;

    private long userId;

    @Min(1)
    @Max(5)
    private int score;

    @NotNull
    private String content;

    private String imageUrl;

    @NotNull
    private LocalDateTime createdAt;

    protected ReviewEntity() {}

    public ReviewEntity(
        long productId,
        long userId,
        int score,
        @NotNull String content,
        @Nullable String imageUrl
    ) {
        this.id = 0L;
        this.productId = productId;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
