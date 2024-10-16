package com.hanghae.project.infrastructure.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue
    private Long id;

    private long restockRound;

    private long quantity;

    protected ProductEntity() {}

    public ProductEntity(long id, long restockRound, long quantity) {
        this.id = id;
        this.restockRound = restockRound;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public long getRestockRound() {
        return restockRound;
    }

    public long getQuantity() {
        return quantity;
    }
}
