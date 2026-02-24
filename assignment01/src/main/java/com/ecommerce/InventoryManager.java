package com.ecommerce;

import java.util.List;

public interface InventoryManager {
    Product findById(String productId);
    List<Product> listAll();
    void decrementStock(String productId);
    void incrementStock(String productId);
}
