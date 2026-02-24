package com.ecommerce;

public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifyOrderCreated(Order order, String customerEmail) {
        System.out.println("Sending email to " + customerEmail + "...");
        System.out.println("Subject: Order Confirmed - " + order.getId());
        System.out.println("Body: Your order total is $" + String.format("%.2f", order.getTotal()));
        System.out.println("[LOG] Order created: " + order.getId() + " for customer " + order.getCustomerId());
        System.out.println("[ANALYTICS] New order: $" + order.getTotal());
    }

    @Override
    public void notifyOrderCancelled(Order order, String customerEmail) {
        System.out.println("[EMAIL] Your order " + order.getId() + " has been cancelled.");
        System.out.println("[LOG] Order cancelled: " + order.getId());
    }

    @Override
    public void notifyOrderShipped(Order order, String customerEmail, String trackingNumber) {
        System.out.println("[EMAIL] Your order " + order.getId() + " has been shipped!");
        System.out.println("Tracking: " + trackingNumber);
        System.out.println("[SMS] Order shipped: " + trackingNumber);
        System.out.println("[LOG] Order shipped: " + order.getId());
    }
}
