package com.mahfuz.ecommerce_backend.product.controller;

import com.mahfuz.ecommerce_backend.common.constants.ApiEndPoints;
import com.mahfuz.ecommerce_backend.common.dto.ApiResponse;
import com.mahfuz.ecommerce_backend.product.dto.ProductCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Product;
import com.mahfuz.ecommerce_backend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndPoints.ProductAdmin.BASE_PRODUCT_ADMIN)
@RequiredArgsConstructor
@Tag(
        name = "Product Admin API",
        description = "API endpoints for managing products in the admin panel"
)
public class ProductAdminController {
    private final ProductService productService;

    @Operation(
            summary = "Create a new product",
            description = "Endpoint to create a new product. Requires admin privileges.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Product created successfully",
                            content = @Content(schema = @Schema(implementation = Product.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Validation error or bad request",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Duplicate SKU or resource conflict",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductCreateRequest request){
        return ResponseEntity.ok(ApiResponse.success(productService.create(request)));
    }

    // Implement endpoint to retrieve product details by ID
    @Operation(
        summary = "Get product details by ID",
        description = "Endpoint to retrieve product details by its ID. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Product details retrieved successfully",
                        content = @Content(schema = @Schema(implementation = Product.class))
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Product not found",
                        content = @Content
                )

        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(productService.getById(id)));
    }

    // Implement paginated product listing with filters (category, active status, price range)

    // Implement endpoint to update product information (name, price, description)

    // Implement functionality to reassign a product to a different category

    // Implement endpoint to activate or deactivate a product (soft delete via isActive flag)

    // Implement endpoint to permanently delete a product
}
