package com.mahfuz.ecommerce_backend.product.controller;

import com.mahfuz.ecommerce_backend.common.constants.ApiEndPoints;
import com.mahfuz.ecommerce_backend.common.dto.ApiResponse;
import com.mahfuz.ecommerce_backend.common.dto.PaginatedResponse;
import com.mahfuz.ecommerce_backend.product.dto.CategoryCreateRequest;
import com.mahfuz.ecommerce_backend.product.dto.CategoryUpdateRequest;
import com.mahfuz.ecommerce_backend.product.entity.Category;
import com.mahfuz.ecommerce_backend.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // Implement endpoint to retrieve category details by ID
    @Operation(
        summary = "Get category details by ID",
        description = "Endpoint to retrieve details of a specific category by its ID.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Category details retrieved successfully",
                        content = @Content(
                                schema = @Schema(implementation = Category.class)
                        )
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Category not found",
                        content = @Content
                )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getById(id)));
    }
    

    // Implement paginated category listing
    @Operation(
        summary = "Get paginated list of categories",
        description = "Endpoint to retrieve a paginated list of categories. Supports pagination parameters like page number and page size.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Paginated list of categories retrieved successfully",
                        content = @Content(
                                schema = @Schema(implementation = PaginatedResponse.class)
                        )
                )
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<Category>>> getCategories(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.success(PaginatedResponse.of(categoryService.getAll(pageable))));
    }

    // Implement endpoint to update category information (name)
    @Operation(
        summary = "Update category information",
        description = "Endpoint to update the name of an existing category. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Category updated successfully",
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
                        responseCode = "404",
                        description = "Category not found",
                        content = @Content
                )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory (@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request){
        return ResponseEntity.ok(ApiResponse.success(categoryService.update(id, request)));
    }

    // Implement endpoint to activate or deactivate a category (soft delete via isActive flag)
    @Operation(
        summary = "Toggle category active status",
        description = "Endpoint to activate or deactivate a category by toggling its isActive flag. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Category active status toggled successfully",
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
                        responseCode = "404",
                        description = "Category not found",
                        content = @Content
                )
        }
    )
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Category>> toggleCategoryStatus(@PathVariable Long id, @RequestParam boolean isActive){
        return ResponseEntity.ok(ApiResponse.success(categoryService.toggleActiveStatus(id, isActive)));
    }

    // Implement endpoint to permanently delete a category
    @Operation(
        summary = "Delete a category",
        description = "Endpoint to permanently delete a category. Requires admin privileges.",
        responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "200",
                        description = "Category deleted successfully",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized - Admin privileges required",
                        content = @Content
                ),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(
                        responseCode = "404",
                        description = "Category not found",
                        content = @Content
                )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
