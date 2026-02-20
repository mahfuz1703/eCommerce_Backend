package com.mahfuz.ecommerce_backend.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mahfuz.ecommerce_backend.common.entity.Auditable;
import com.mahfuz.ecommerce_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends BaseEntity implements Auditable {
    @Column(nullable = false, length = 100, unique = true)
    private String sku;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

    @Column(length = 250)
    private String imageUrl;

    // Auditing fields
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "created_by", nullable = true)
    private Long createdBy;

    @Column(name = "modified_by", nullable = true)
    private Long modifiedBy;
}
