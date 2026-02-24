package com.ecommerce;

import java.util.List;

public class Order {
    private final String id;
    private final String customerId;
    private final List<OrderItem> items;
    private double total;
    private String status;
    private final String shippingAddress;

    public Order(String id, String customerId, List<OrderItem> items, double total, String status, String shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.total = total;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getShippingAddress() { return shippingAddress; }
}
