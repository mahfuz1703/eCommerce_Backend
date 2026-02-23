package com.mahfuz.ecommerce_backend.cart.service;

import com.mahfuz.ecommerce_backend.cart.dto.CartRequest;
import com.mahfuz.ecommerce_backend.cart.entity.Cart;

public interface CartService {
    void addOrUpdateCartItem(Long productId, CartRequest request);

    Cart getCartByUserId(Long userId);
}
