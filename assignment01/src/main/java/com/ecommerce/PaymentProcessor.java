package com.ecommerce;

public interface PaymentProcessor {
    boolean process(double amount);
}
