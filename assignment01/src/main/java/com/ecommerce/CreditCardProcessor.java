package com.ecommerce;

public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public boolean process(double amount) {
        System.out.println("Processing credit card payment...");
        return Math.random() > 0.1; // 90% success
    }
}
