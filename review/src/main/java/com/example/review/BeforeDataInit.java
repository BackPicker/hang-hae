package com.example.review;

import com.example.review.domain.Product;
import com.example.review.domain.Review;
import com.example.review.domain.User;
import com.example.review.repository.ProductRepository;
import com.example.review.repository.ReviewRepository;
import com.example.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
public class BeforeDataInit {

    private final UserRepository    userRepository;
    private final ReviewRepository  reviewRepository;
    private final ProductRepository productRepository;


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        // 샘플 Product 생성 및 저장
        Product product = new Product(0, 0.0f);
        productRepository.save(product);

        // 샘플 User 생성 및 저장
        User userA = new User("sampleUser A"); // User 생성자에 맞게 조정
        User userB = new User("sampleUser B"); // User 생성자에 맞게 조정
        userRepository.save(userA);
        userRepository.save(userB);

        // 샘플 Review 생성 및 저장
        for (int i = 0; i < 100; i++) {
            Review review = new Review(product, userA, new Random().nextInt(4) + 1, UUID.randomUUID()
                    .toString()
                    .substring(0, 8), "http://example.com/image.jpg");
            reviewRepository.save(review);
        }
    }
}