package com.example.review.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodict_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Max(value = 5)
    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    public Review(Product product, User user, Integer score, String content) {
        this.product   = product;
        this.user      = user;
        this.score     = score;
        this.content   = content;
        this.createdAt = LocalDateTime.now();
    }

    public Review(Product product, User user, Integer score, String content, String imageUrl) {
        this.product   = product;
        this.user      = user;
        this.score     = score;
        this.content   = content;
        this.imageUrl  = imageUrl;
        this.createdAt = LocalDateTime.now();
    }
}
