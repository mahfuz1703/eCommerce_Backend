package com.mahfuz.ecommerce_backend.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryUpdateRequest(
    @NotNull
    @Min(value = 0, message = "Quantity must be grater than or equal to 0")
    Integer quantity
) {
}
