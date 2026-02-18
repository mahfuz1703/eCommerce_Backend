package com.mahfuz.ecommerce_backend.product.entity;

import com.mahfuz.ecommerce_backend.common.entity.Auditable;
import com.mahfuz.ecommerce_backend.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity implements Auditable {
    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "created_by", nullable = true)
    private Long createdBy;

    @Column(name = "modified_by", nullable = true)
    private Long modifiedBy;
}
