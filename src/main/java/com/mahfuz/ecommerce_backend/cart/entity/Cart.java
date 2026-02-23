package com.mahfuz.ecommerce_backend.cart.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mahfuz.ecommerce_backend.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "Cart.withItems",
        attributeNodes = @NamedAttributeNode("items")
)
public class Cart extends BaseEntity{
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Transient
    public double getTotalPrice(){
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum();
    }

    @PrePersist
    protected void onCreate(){
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        modifiedAt = now;
    }

    @PreUpdate
    protected void onUpdate(){
        modifiedAt = LocalDateTime.now();
    }
}
