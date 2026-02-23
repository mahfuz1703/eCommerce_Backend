package com.mahfuz.ecommerce_backend.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartRequest(
    @NotNull(message = "User ID is required")
    Long userId,

    @NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Quantity must be at least 0")
    Integer quantity
) {}
