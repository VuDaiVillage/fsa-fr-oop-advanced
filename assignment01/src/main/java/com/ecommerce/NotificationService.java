package com.ecommerce;

public interface NotificationService {
    void notifyOrderCreated(Order order, String customerEmail);
    void notifyOrderCancelled(Order order, String customerEmail);
    void notifyOrderShipped(Order order, String customerEmail, String trackingNumber);
}
