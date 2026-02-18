package com.mahfuz.ecommerce_backend.product.controller;

import com.mahfuz.ecommerce_backend.common.constants.ApiEndPoints;
import com.mahfuz.ecommerce_backend.common.dto.ApiResponse;
import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import com.mahfuz.ecommerce_backend.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndPoints.CategoryAdmin.BASE_CATEGORY_ADMIN)
@Tag(
        name = "Category Admin API",
        description = "API endpoints for managing product categories in the admin panel"
)
public class CategoryAdminController {
    private final CategoryService categoryService;

    @Operation(
            summary = "Create a new category",
            description = "Endpoint to create a new product category. Requires admin privileges.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Category created successfully",
                            content = @Content(
                                    schema = @Schema(implementation = Category.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Admin privileges required",
                            content = @Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "409",
                            description = "Conflict - Category with the same code already exists",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody CategoryCreateRequest request){
        return ResponseEntity.ok(ApiResponse.success(categoryService.create(request)));
    }
}
