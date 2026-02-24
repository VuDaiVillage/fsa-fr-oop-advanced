package com.ecommerce;

public class PaypalProcessor implements PaymentProcessor {
    @Override
    public boolean process(double amount) {
        System.out.println("Redirecting to PayPal...");
        return Math.random() > 0.05; // 95% success
    }
}
