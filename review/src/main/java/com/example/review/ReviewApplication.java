package com.example.review;

import com.example.review.repository.ProductRepository;
import com.example.review.repository.ReviewRepository;
import com.example.review.repository.UserRepository;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewApplication.class, args);
    }

    @Bean
    Hibernate5JakartaModule hibernate5JakartaModule() {
        return new Hibernate5JakartaModule();
    }

    @Bean
    public BeforeDataInit beforeDataInit(UserRepository userRepository, ProductRepository productRepository, ReviewRepository reviewRepository) {
        return new BeforeDataInit(userRepository, reviewRepository, productRepository);
    }
}
