package com.ecommerce;

public class OrderItem {
    private final String productId;
    private final String name;
    private final double price;

    public OrderItem(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
