package com.example.restock;

import com.example.restock.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestockApplication.class, args);
    }


    @Bean
    public BeforeDataInit beforeDataInit(ProductRepository productRepository) {
        return new BeforeDataInit(productRepository);
    }
}
