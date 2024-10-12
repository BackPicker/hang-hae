package com.example.restock;

import com.example.restock.domain.Product;
import com.example.restock.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class BeforeDataInit {
    private final ProductRepository productRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void beforeDataInit() {
        Product itemA = new Product("itemA", 50, 0);
        Product itemB = new Product("itemB", 5, 3);

        productRepository.save(itemA);
        productRepository.save(itemB);

    }

}
