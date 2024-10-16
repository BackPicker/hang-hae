package com.hanghae.project.domain.product;

public record Product(
    long id,
    long restockRound,
    long quantity
) {
    public Product stock() {
        return new Product(id, restockRound + 1, quantity);
    }

    public boolean isSoldOut() {
        return quantity == 0L;
    }
}
