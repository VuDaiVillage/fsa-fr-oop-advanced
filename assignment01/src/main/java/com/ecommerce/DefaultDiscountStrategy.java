package com.ecommerce;

public class DefaultDiscountStrategy implements DiscountStrategy {
    @Override
    public double apply(Product product) {
        double price = product.getPrice();
        String category = product.getCategory();

        if ("ELECTRONICS".equals(category) && price > 500) {
            return price * 0.95; // 5% off expensive electronics
        } else if ("CLOTHING".equals(category)) {
            return price * 0.90; // 10% off clothing
        }
        // No discount for other categories by default
        return price;
    }
}
