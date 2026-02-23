package com.mahfuz.ecommerce_backend.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahfuz.ecommerce_backend.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    @EntityGraph(value = "Cart.withItems", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Cart> findByUserId(Long userId);
}
