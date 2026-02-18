package com.mahfuz.ecommerce_backend.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotNull(message = "Category name is required")
        @Size(max = 120, message = "Category name must not exceed 120 characters")
        String name,

        @NotNull(message = "Category code is required")
        @Size(max = 50, message = "Category code must not exceed 50 characters")
        String code
) {
}
