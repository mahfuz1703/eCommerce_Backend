package com.mahfuz.ecommerce_backend.cart.service.impl;

import org.springframework.stereotype.Service;

import com.mahfuz.ecommerce_backend.cart.dto.CartRequest;
import com.mahfuz.ecommerce_backend.cart.entity.Cart;
import com.mahfuz.ecommerce_backend.cart.entity.CartItem;
import com.mahfuz.ecommerce_backend.cart.repository.CartRepository;
import com.mahfuz.ecommerce_backend.cart.service.CartService;
import com.mahfuz.ecommerce_backend.product.entity.Product;
import com.mahfuz.ecommerce_backend.product.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void addOrUpdateCartItem(Long productId, CartRequest request){
        Cart cart = cartRepository.findByUserId(request.userId())
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .userId(request.userId())
                                .build())
                );
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        
        CartItem existingItem = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct()
                    .getId().equals(product.getId())).findFirst().orElse(null);

        if(existingItem != null){
            if(request.quantity() == 0){
                cart.getItems().remove(existingItem);
            } else {
                existingItem.setQuantity(request.quantity());
            }
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.quantity())
                    .unitPrice(product.getPrice())
                    .build();
            cart.getItems().add(newItem);
        }
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId){
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user id: " + userId));
    }
}
