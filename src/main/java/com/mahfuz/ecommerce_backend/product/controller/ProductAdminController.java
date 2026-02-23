package com.mahfuz.ecommerce_backend.product.controller;

import com.mahfuz.ecommerce_backend.common.constants.ApiEndPoints;
import com.mahfuz.ecommerce_backend.common.dto.ApiResponse;
import com.mahfuz.ecommerce_backend.common.dto.PaginatedResponse;
import com.mahfuz.ecommerce_backend.product.dto.InventoryUpdateRequest;
import com.mahfuz.ecommerce_backend.product.dto.ProductCreateRequest;
import com.mahfuz.ecommerce_backend.product.dto.ProductUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Product;
import com.mahfuz.ecommerce_backend.product.service.InventoryService;
import com.mahfuz.ecommerce_backend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final InventoryService inventoryService;

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

    // Implement paginated product listing with filters
    @Operation(
        summary = "Get paginated list of products",
        description = "Endpoint to retrieve a paginated list of products with optional filters for category, price range, and availability. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Paginated list of products retrieved successfully",
                        content = @Content(schema = @Schema(implementation = PaginatedResponse.class))
                )
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<Product>>> getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                              @RequestParam(value = "size", defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(PaginatedResponse.of(productService.getAll(pageable))));
    }

    // Implement endpoint to update product information (name, price, description)
    @Operation(
        summary = "Update product information",
        description = "Endpoint to update product information such as name, price, and description. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Product updated successfully",
                        content = @Content(schema = @Schema(implementation = Product.class))
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Validation error or bad request",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Product not found",
                        content = @Content
                )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request){
        return ResponseEntity.ok(ApiResponse.success(productService.update(id, request)));
    }

    // Implement functionality to reassign a product to a different category
    @Operation(
        summary = "Update product category",
        description = "Endpoint to reassign a product to a different category. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Product category updated successfully",
                        content = @Content(schema = @Schema(implementation = Product.class))
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Product or category not found",
                        content = @Content
                )
        }
    )
    @PutMapping("/{id}/category")
    public ResponseEntity<ApiResponse<Product>> updateProductCategory(@PathVariable Long id, @RequestParam String categoryCode){
        return ResponseEntity.ok(ApiResponse.success(productService.updateCategory(id, categoryCode)));
    }

    // Implement endpoint to activate or deactivate a product (soft delete via isActive flag)
    @Operation(
        summary = "Toggle product active status",
        description = "Endpoint to activate or deactivate a product using the isActive flag. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Product active status updated successfully",
                        content = @Content(schema = @Schema(implementation = Product.class))
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Product not found",
                        content = @Content
                )
        }
    )
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Product>> toggleProductStatus(@PathVariable Long id, @RequestParam boolean isActive){
        return ResponseEntity.ok(ApiResponse.success(productService.toggleActiveStatus(id, isActive)));
    }

    // Implement endpoint to permanently delete a product
    @Operation(
        summary = "Delete a product",
        description = "Endpoint to permanently delete a product. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Product deleted successfully",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Product not found",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized - Admin privileges required",
                        content = @Content
                ),
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //     Implement endpoint to update product inventory (stock quantity)
    @Operation(
        summary = "Update product inventory",
        description = "Endpoint to update the stock quantity of a product. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Product inventory updated successfully",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "400",
                        description = "Validation error or bad request",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Product not found",
                        content = @Content
                )
        }
    )
    @PutMapping(ApiEndPoints.ProductAdmin.PRODUCT_INVENTORY)
    public ResponseEntity<ApiResponse<Void>> updateStock(@PathVariable Long productId, @Valid @RequestBody InventoryUpdateRequest request){
        inventoryService.updateStock(productId, request);
        return ResponseEntity.ok(ApiResponse.success("Stock updated successfully", null));
    }
}
