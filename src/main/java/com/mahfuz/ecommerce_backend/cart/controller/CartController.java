package com.mahfuz.ecommerce_backend.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahfuz.ecommerce_backend.cart.dto.CartRequest;
import com.mahfuz.ecommerce_backend.cart.entity.Cart;
import com.mahfuz.ecommerce_backend.cart.service.CartService;
import com.mahfuz.ecommerce_backend.common.constants.ApiEndPoints;
import com.mahfuz.ecommerce_backend.common.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiEndPoints.Cart.BASE_CART)
@RequiredArgsConstructor
@Tag(
    name = "Cart API",
    description = "Endpoints for managing shopping cart"
)
public class CartController {
    private final CartService cartService;

    @Operation(
        summary = "Add or update cart item",
        description = """
            Adds a product to the user's cart or updates its quantity if it already exists.
                    If quantity is set to 0, the product will be removed from the cart.
                    A new cart is automatically created if the user does not have one yet.
            """,
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Cart item added or updated successfully",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Invalid request data",
                content = @Content
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Product not found with the given ID",
                content = @Content
            )
        }
    )
    @PostMapping(ApiEndPoints.Cart.ADD_CART_ITEM)
    public ResponseEntity<ApiResponse<Void>> createOrUpdateCart(@PathVariable Long productId, @Valid @RequestBody CartRequest request){
        cartService.addOrUpdateCartItem(productId, request);
        return ResponseEntity.ok(ApiResponse.success("Item added to cart successfully", null));
    }

    @Operation(
        summary = "View cart",
        description = "Retrieves the current state of the user's cart, including all items and total price.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Cart retrieved successfully",
                content = @Content(schema = @Schema(implementation = Cart.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Cart not found for the user",
                content = @Content
            )
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> viewCart(@RequestParam Long userId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCartByUserId(userId)));
    }
}
