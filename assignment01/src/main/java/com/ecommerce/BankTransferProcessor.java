package com.ecommerce;

public class BankTransferProcessor implements PaymentProcessor {
    @Override
    public boolean process(double amount) {
        System.out.println("Waiting for bank transfer confirmation...");
        return true; // assume success
    }
}
