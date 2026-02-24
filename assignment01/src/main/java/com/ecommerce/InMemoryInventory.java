package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class InMemoryInventory implements InventoryManager {
    private final List<Product> products = new ArrayList<>();

    public InMemoryInventory() {
        products.add(new Product("P001", "Laptop", 999.99, 50, "ELECTRONICS"));
        products.add(new Product("P002", "T-Shirt", 29.99, 200, "CLOTHING"));
        products.add(new Product("P003", "Coffee Beans", 15.99, 100, "FOOD"));
        products.add(new Product("P004", "Headphones", 149.99, 75, "ELECTRONICS"));
    }

    @Override
    public Product findById(String productId) {
        for (Product p : products) {
            if (p.getId().equals(productId)) return p;
        }
        return null;
    }

    @Override
    public List<Product> listAll() { return new ArrayList<>(products); }

    @Override
    public void decrementStock(String productId) {
        Product p = findById(productId);
        if (p != null) p.setStock(p.getStock() - 1);
    }

    @Override
    public void incrementStock(String productId) {
        Product p = findById(productId);
        if (p != null) p.setStock(p.getStock() + 1);
    }
}
