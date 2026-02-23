package com.mahfuz.ecommerce_backend.product.service.impl;

import org.springframework.stereotype.Service;

import com.mahfuz.ecommerce_backend.product.dto.InventoryUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Inventory;
import com.mahfuz.ecommerce_backend.product.repository.InventoryRepository;
import com.mahfuz.ecommerce_backend.product.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public void updateStock(Long productId, InventoryUpdateRequest request){
        Inventory inventory = inventoryRepository.findByProductId(productId)
            .orElseThrow(() -> new RuntimeException("Inventory not found for product id: " + productId));
        
        inventory.setQuantity(inventory.getQuantity() + request.quantity());
        inventoryRepository.save(inventory);
    }
}
