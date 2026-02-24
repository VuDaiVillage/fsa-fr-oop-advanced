package com.ecommerce;

public class BlackFridayDiscount implements DiscountStrategy {
    private final double percent; // e.g., 0.20 for 20%

    public BlackFridayDiscount(double percent) {
        this.percent = percent;
    }

    @Override
    public double apply(Product product) {
        return product.getPrice() * (1 - percent);
    }
}
