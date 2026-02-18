package com.mahfuz.ecommerce_backend.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest (
        @NotNull(message = "sku is required")
        @Size(max = 100, message = "sku must not exceed 100 characters")
        String sku,

        @NotNull(message = "name is required")
        @Size(max = 200, message = "name must not exceed 200 characters")
        String name,

        @Size(max = 1000, message = "description must not exceed 1000 characters")
        String description,

        @NotNull(message = "price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
        double price,

        @NotNull(message = "categoryId is required")
        @JsonProperty("category_id")
        String categoryCode,

        @Size(max = 250, message = "imageUrl must not exceed 250 characters")
        @JsonProperty("image_url")
        String imageUrl
){
}
