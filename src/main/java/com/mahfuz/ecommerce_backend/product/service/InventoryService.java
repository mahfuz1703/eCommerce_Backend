package com.mahfuz.ecommerce_backend.product.service;

import com.mahfuz.ecommerce_backend.product.dto.InventoryUpdateRequest;

public interface InventoryService {
    void updateStock(Long productId, InventoryUpdateRequest request);
}
