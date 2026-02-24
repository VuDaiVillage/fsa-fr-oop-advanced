package com.ecommerce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final InventoryManager inventory;
    private final DiscountStrategy discountStrategy;
    private final Map<String, PaymentProcessor> paymentProcessors = new HashMap<>();
    private final NotificationService notificationService;

    // Convenience constructor with defaults
    public OrderService() {
        this(new InMemoryInventory(), new DefaultDiscountStrategy(), new ConsoleNotificationService());
        paymentProcessors.put("CREDIT_CARD", new CreditCardProcessor());
        paymentProcessors.put("PAYPAL", new PaypalProcessor());
        paymentProcessors.put("BANK_TRANSFER", new BankTransferProcessor());
    }

    // Main constructor for DI
    public OrderService(InventoryManager inventory, DiscountStrategy discountStrategy, NotificationService notificationService) {
        this.inventory = inventory;
        this.discountStrategy = discountStrategy;
        this.notificationService = notificationService;
        // Default processors
        paymentProcessors.put("CREDIT_CARD", new CreditCardProcessor());
        paymentProcessors.put("PAYPAL", new PaypalProcessor());
        paymentProcessors.put("BANK_TRANSFER", new BankTransferProcessor());
    }

    public void registerPaymentProcessor(String key, PaymentProcessor processor) {
        paymentProcessors.put(key, processor);
    }

    public String createOrder(String customerId, String customerEmail, List<String> productIds,
                              String paymentMethod, String shippingAddress) {
        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        // Validate and reserve
        for (String pid : productIds) {
            Product p = inventory.findById(pid);
            if (p == null) {
                System.out.println("ERROR: Product not found: " + pid);
                return null;
            }
            if (p.getStock() <= 0) {
                System.out.println("ERROR: Out of stock: " + p.getName());
                return null;
            }

            double discounted = discountStrategy.apply(p);
            items.add(new OrderItem(p.getId(), p.getName(), discounted));
            total += discounted;
            inventory.decrementStock(pid);
        }

        // Apply payment method fee
        if ("CREDIT_CARD".equals(paymentMethod)) {
            total = total * 1.03;
        } else if ("PAYPAL".equals(paymentMethod)) {
            total = total * 1.025;
        }

        PaymentProcessor processor = paymentProcessors.get(paymentMethod);
        if (processor == null) {
            System.out.println("ERROR: Unsupported payment method: " + paymentMethod);
            // restore stock
            for (OrderItem it : items) inventory.incrementStock(it.getProductId());
            return null;
        }

        boolean paymentSuccess = processor.process(total);
        if (!paymentSuccess) {
            System.out.println("ERROR: Payment failed!");
            for (OrderItem it : items) inventory.incrementStock(it.getProductId());
            return null;
        }

        String orderId = "ORD-" + System.currentTimeMillis();
        Order order = new Order(orderId, customerId, items, total, "CONFIRMED", shippingAddress);
        orders.add(order);

        notificationService.notifyOrderCreated(order, customerEmail);

        return orderId;
    }

    public void cancelOrder(String orderId, String customerEmail) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                if ("CONFIRMED".equals(order.getStatus())) {
                    order.setStatus("CANCELLED");
                    for (OrderItem it : order.getItems()) inventory.incrementStock(it.getProductId());
                    notificationService.notifyOrderCancelled(order, customerEmail);
                } else {
                    System.out.println("ERROR: Cannot cancel order in status: " + order.getStatus());
                }
                return;
            }
        }
        System.out.println("ERROR: Order not found: " + orderId);
    }

    public void shipOrder(String orderId, String customerEmail, String trackingNumber) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)) {
                if ("CONFIRMED".equals(order.getStatus())) {
                    order.setStatus("SHIPPED");
                    notificationService.notifyOrderShipped(order, customerEmail, trackingNumber);
                }
                return;
            }
        }
    }

    public void printAllOrders() {
        for (Order order : orders) {
            System.out.println("Order: " + order.getId() + " - Status: " + order.getStatus() + " - Total: $" + order.getTotal());
        }
    }
}
